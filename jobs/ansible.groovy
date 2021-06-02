import org.jenkinsci.plugins.scriptsecurity.scripts.ScriptApproval
import groovy.transform.Field

@Field String jenkinsCredentialId = "SSH_GIT_KEY"
@Field String basePath = 'ansible'
@Field String defaultPollingScm = 'H/5 * * * *'

JobConstructor[] jobDefnList = [
        [
                "ansible-telegraf",
                "git@github.com:dj-wasabi/ansible-telegraf.git",
                defaultPollingScm
        ]
]

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

folder(basePath) {
    description "Overview of all " + basePath + " related jobs."
}

jobDefnList.each { job ->
    println "[INFO] Generating view... " + basePath
    println "[INFO] Generating job... " + job.jobName

    def index = 0
    def jobName = job.jobName
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
