package com.djwasabi.common.workers

class ResultsGetter {

    def script

    ResultsGetter(def script) {
        this.script = script
    }

    void repeatPreviousBuildResult(currentBuild) {
        def lastResult = currentBuild.rawBuild.getPreviousBuild()?.result
        println("Result of previous build was ${lastResult}")
        if (!hudson.model.Result.SUCCESS.equals(lastResult)) {
            println("Copying failed result of previous build: ${env.JENKINS_URL}${currentBuild.rawBuild.getPreviousBuild()?.url}")
            while (currentBuild.rawBuild.getPreviousBuild().result == null) {
                sleep(5)
                println "Waiting for previous build to finish..."
            }
            currentBuild.setResult(currentBuild.rawBuild.getPreviousBuild()?.result?.toString())
        }
    }

}
