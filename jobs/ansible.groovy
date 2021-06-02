import groovy.transform.Field

@Field String jenkinsCredentialId = "basic-SSH"
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

    def index = 0

    multibranchPipelineJob(basePath + "/" + job.jobName) {
        displayName(job.jobName)
        branchSources {
            git {
                remote(job.jobGitUrl)
                credentialsId(jenkinsCredentialId)
            }
        }
        triggers {
            cron(job.jobPollingScm)
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