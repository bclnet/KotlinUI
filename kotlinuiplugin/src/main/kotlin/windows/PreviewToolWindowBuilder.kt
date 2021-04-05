package kotlinuiplugin.windows

import kotlinuiplugin.utils.Utils
import javax.swing.*

object PreviewToolWindowBuilder {

    fun empty(panel: JPanel) {
        val lblMessage = JLabel("Empty Message")
        lblMessage.setBounds(30, 25, 83, 16)
        panel.add(lblMessage)
    }

    fun error(panel: JPanel, error: String) {
        val lblMessage = JLabel(error)
        lblMessage.setBounds(30, 25, 83, 16)
        panel.add(lblMessage)
    }

    fun sample(panel: JPanel) {
        val lblUsername = JLabel(Utils.message("settings.username"))
        lblUsername.setBounds(30, 25, 83, 16)
        panel.add(lblUsername)

        val lblPassword = JLabel(Utils.message("settings.token"))
        lblPassword.setBounds(30, 74, 83, 16)
        panel.add(lblPassword)

        val lblUrl = JLabel(Utils.message("settings.jiraUrl"))
        lblUrl.setBounds(30, 123, 83, 16)
        panel.add(lblUrl)

        val lblRegEx = JLabel(Utils.message("settings.regEx"))
        lblRegEx.setBounds(30, 172, 83, 16)
        panel.add(lblRegEx)

        val txtUsername: JTextField = JTextField()
        txtUsername.setBounds(125, 20, 291, 26)
        txtUsername.columns = 10
        panel.add(txtUsername)

        val tokenField: JPasswordField = JPasswordField()
        tokenField.setBounds(125, 69, 291, 26)
        panel.add(tokenField)

        val txtUrl: JTextField = JTextField()
        txtUrl.setBounds(125, 118, 291, 26)
        txtUrl.columns = 10
        panel.add(txtUrl)

        val txtRegEx: JTextField = JTextField()
        txtRegEx.setBounds(125, 167, 291, 26)
        txtRegEx.columns = 10
        panel.add(txtRegEx)
    }
}