package com.bmx

class PowershellExecutor {
    def reportStatusIfAny(String command, String type, StringBuilder content) {
        if (!content.allWhitespace) {
            println type + ':\n' + command + "\n" + content
        }
    }

    def execute(String command) {
        def psCommand = "powershell -command \"${command}\""
        def sout = new StringBuilder()
        def serr = new StringBuilder()
        def proc = psCommand.execute()
        proc.consumeProcessOutput(sout, serr)
        proc.waitForOrKill(1000)
        reportStatusIfAny(psCommand, 'Message', sout)
        reportStatusIfAny(psCommand, 'Error  ', serr)
    }
}
