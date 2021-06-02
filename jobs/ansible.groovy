import org.jenkinsci.plugins.scriptsecurity.scripts.ScriptApproval
import groovy.transform.Field

@Field String jenkinsCredentialId = "SSH_GIT_KEY"
@Field String basePath = 'ansible'
@Field String defaultPollingScm = 'H/5 * * * *'

class JobConstructor {
    def jobName = ""
    def jobGitUrl = ""
    def jobPollingScm = ""

    private JobConstructor(String jobName, String jobGitUrl, String jobPollingScm) {
        this.jobName = jobName
        this.jobGitUrl = jobGitUrl
        this.jobPollingScm = jobPollingScm
    }
}

JobConstructor[] jobDefnList = [
        [
                "ansible-telegraf",
                "git@github.com:dj-wasabi/ansible-telegraf.git",
                defaultPollingScm
        ]
]

folder(basePath) {
    description basePath + " jobs"
}

jobDefnList.each { job ->
    println "[INFO] Generating view... " + basePath
    println "[INFO] Generating job... " + job.jobName

    Random random = new Random()
    def index = 0
    def jobName = job.jobName
    def jobNameID = random.nextInt(10 ** 10)
    def jobGitUrl = job.jobGitUrl
    def jobPollingScm = job.jobPollingScm

    multibranchPipelineJob(basePath + "/" + jobName) {
        displayName(jobName)
        branchSources {
            git {
                id(jobName)
                remote(jobGitUrl)
                credentialsId(jenkinsCredentialId)
            }
        }
        triggers {
            cron(jobPollingScm)
        }
    }
}

listView(basePath) {
    recurse(true)
    jobs {
        jobDefnList.each { job ->
            name(basePath + "/" + job.jobName)
        }
    }
    columns {
        status()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
        buildButton()
    }
}

buildMonitorView(basePath + "-dashboard") {
    recurse(true)
    jobs {
        jobDefnList.each { job ->
            name(basePath + "/" + job.jobName)
        }
    }
}
