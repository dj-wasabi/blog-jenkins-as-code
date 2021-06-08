package com.djwasabi.common.workers

class Command {
    def script

    Command(def script) {
        this.script = script
    }

    void echo(message) {
        this.script.sh('echo ' + message)
    }
}
