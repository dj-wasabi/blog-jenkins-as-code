package com.djwasabi.common

import com.djwasabi.common.workers.*
import groovy.transform.Field

def pipeline(jobName, name = "world", agentNode = "worker") {
    def command = new Command(this)
    def git = new Git(this)
    def resultsGetter = new ResultsGetter(this)

    node(agentNode) {
        try {
            def lastResult = currentBuild.rawBuild.getPreviousBuild()?.result

            stage("Checkout") {
                git.checkout()
                tagged = git.isCurrentCommitAlreadyTagged()
            }
            if (!tagged || hudson.model.Result.SUCCESS != lastResult) {
                stage("Run command") {
                    command.echo("hello " + name + " via job " + jobName)
                }
            } else {
                resultsGetter.repeatPreviousBuildResult(currentBuild)
            }
        } catch (all) {
            stage('Destroy it') {
                command.echo('lets run when things go wrong.')
            }
        }
    }
}
