package com.djwasabi.common.workers

class Git {
    def script

    Git(def script) {
        this.script = script
    }

    void checkout() {
        this.script.checkout this.script.scm
        this.script.sh('git rev-parse HEAD > .git/commit-id')
    }

    String getBranchName() {
        isMasterBranch() ? '' : this.script.env.BRANCH_NAME;
    }

    boolean isMasterBranch() {
        this.script.env.BRANCH_NAME in ['master', 'main', '', null]
    }

    boolean isCurrentCommitAlreadyTagged() {
        return false
    }

}
