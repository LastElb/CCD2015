/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 */
package jchess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Class responible for drawing Network Settings, when player want to start
 * a game on a network
 *
 */
public class DrawNetworkSettings extends JPanel implements ActionListener {

    private JDialog parent;
    private GridBagLayout gbl;
    private GridBagConstraints gbc;
    private ButtonGroup serverORclient;
    private JRadioButton radioServer;
    private JRadioButton radioClient;
    private JLabel labelNick;
    private JLabel labelPassword;
    private JLabel labelGameID;
    private JLabel labelOptions;
    private JPanel panelOptions;
    private JTextField textNick;
    private JPasswordField textPassword;
    private JTextField textGameID;
    private JButton buttonStart;
    private ClientOptionsPanel clientOptions;

    DrawNetworkSettings(JDialog parent) {
        super();

        //components
        this.parent = parent;

        this.radioServer = new JRadioButton("Server starten", true);
        this.radioClient = new JRadioButton("Mit Sevrer verbinden", false);
        this.serverORclient = new ButtonGroup();
        this.serverORclient.add(this.radioServer);
        this.serverORclient.add(this.radioClient);
        this.radioServer.addActionListener(this);
        this.radioClient.addActionListener(this);

        this.labelNick = new JLabel("Nutzername");
        this.labelGameID = new JLabel("Game-ID");

        this.labelOptions = new JLabel("Server Optionen");

        this.textNick = new JTextField();
        this.textGameID = new JTextField();
        this.textGameID.setEnabled(false);

        this.panelOptions = new JPanel();
        this.clientOptions = new ClientOptionsPanel();

        this.buttonStart = new JButton("Start");
        this.buttonStart.addActionListener(this);

        //add components
        this.gbl = new GridBagLayout();
        this.gbc = new GridBagConstraints();
        this.gbc.fill = GridBagConstraints.BOTH;
        this.setLayout(gbl);

        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.gbl.setConstraints(radioServer, gbc);
        this.add(radioServer);

        this.gbc.gridx = 1;
        this.gbc.gridy = 0;
        this.gbl.setConstraints(radioClient, gbc);
        this.add(radioClient);

        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbc.gridwidth = 2;
        this.gbl.setConstraints(labelGameID, gbc);
        this.add(labelGameID);

        this.gbc.gridx = 0;
        this.gbc.gridy = 2;
        this.gbc.gridwidth = 2;
        this.gbl.setConstraints(textGameID, gbc);
        this.add(textGameID);

        this.gbc.gridx = 0;
        this.gbc.gridy = 3;
        this.gbc.gridwidth = 2;
        this.gbl.setConstraints(labelNick, gbc);
        this.add(labelNick);

        this.gbc.gridx = 0;
        this.gbc.gridy = 4;
        this.gbc.gridwidth = 2;
        this.gbl.setConstraints(textNick, gbc);
        this.add(textNick);

        this.gbc.gridx = 0;
        this.gbc.gridy = 5;
        this.gbc.gridwidth = 2;
        this.gbl.setConstraints(labelOptions, gbc);
        this.add(labelOptions);

        this.gbc.gridx = 0;
        this.gbc.gridy = 6;
        this.gbc.gridwidth = 2;
        this.gbl.setConstraints(panelOptions, gbc);
        this.add(panelOptions);

        this.gbc.gridx = 0;
        this.gbc.gridy = 7;
        this.gbc.gridwidth = 2;
        this.gbl.setConstraints(buttonStart, gbc);
        this.add(buttonStart);
    }

    /*Method for showing settings which the player is intrested with
     */
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == this.radioServer) //show options for server
        {
            this.textGameID.setEnabled(false); // disable gameID
            this.panelOptions.removeAll();
            this.panelOptions.revalidate();
            this.panelOptions.requestFocus();
            this.panelOptions.repaint();
        } else if (arg0.getSource() == this.radioClient) //show options for client
        {
            this.textGameID.setEnabled(true); // disable gameID
            this.panelOptions.removeAll();
            this.panelOptions.add(clientOptions);
            this.panelOptions.revalidate();
            this.panelOptions.requestFocus();
            this.panelOptions.repaint();
        } else if (arg0.getSource() == this.buttonStart) //click start button
        {
            String error = "";
            if (this.textGameID.getText().isEmpty()) {
                error = "Bitte geben Sie eine Game ID an.\n";
            }
            if (this.textNick.getText().length() == 0) {
                error += "Bitte geben Sie einen Nickname an.\n";
            }
            if (this.radioClient.isSelected() && this.clientOptions.textServIP.getText().length() == 0) {
                error += "Bitte geben Sie eine IP an.\n";
            } else if (this.radioClient.isSelected()) {
                Pattern ipPattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
                if (!ipPattern.matcher(this.clientOptions.textServIP.getText()).matches()) {
                    error += "Bitte geben Sie eine gültige IP an.\n";
                }
            }
            if (error.length() > 0) {
                JOptionPane.showMessageDialog(this, error);
                return;
            }

            if (this.radioServer.isSelected()) {
                // @ToDo Start server
            }

            // @ToDo Start Client (do we really want to do it here?)
            // Start Client an Join Game
            // GameID: textGameID.getText()
            // view only: clientOptions.checkOnlyWatch.isSelected()
            // nickname: textNick.getText()

           /*
            Client client;
            try {
                client = new Client(clientOptions.textServIP.getText(), 8080);//create client
                boolean isJoining = client.join(Integer.parseInt(textGameID.getText()), !clientOptions.checkOnlyWatch.isSelected(), textNick.getText(), MD5.encrypt(textPassword.getText()));//join and wait for all players

                if (isJoining) //Client connection: succesful
                {
                    System.out.println("Client connection: succesful");
                    //create new game and draw chessboard
                    Game newGUI = JChessApp.jcv.addNewTab("Network game, table: " + textGameID.getText());
                    client.game = newGUI;
                    newGUI.chessboard.draw();

                    Thread thread = new Thread(client);
                    thread.start(); //client listening

                    this.parent.setVisible(false);//hide parent
                } else {
                    JOptionPane.showMessageDialog(this, Settings.lang("error_connecting_to_server"));
                }

            } catch (Error err) {
                System.out.println("Client connection: failure");
                JOptionPane.showMessageDialog(this, err);
            }
        */
        }
    }

    /* Method responible for drawing clients panel
     */
    private class ClientOptionsPanel extends JPanel //options for client
    {

        public JTextField textServIP;
        public JCheckBox checkOnlyWatch;
        private GridBagLayout gbl;
        private GridBagConstraints gbc;
        private JLabel labelServIP;

        ClientOptionsPanel() {
            super();

            this.labelServIP = new JLabel("Server IP");
            this.textServIP = new JTextField();
            this.checkOnlyWatch = new JCheckBox("Spiel beobachten");

            this.gbl = new GridBagLayout();
            this.gbc = new GridBagConstraints();
            this.gbc.fill = GridBagConstraints.BOTH;
            this.setLayout(gbl);

            this.gbc.gridx = 0;
            this.gbc.gridy = 0;
            this.gbl.setConstraints(labelServIP, gbc);
            this.add(labelServIP);

            this.gbc.gridx = 0;
            this.gbc.gridy = 1;
            this.gbl.setConstraints(textServIP, gbc);
            this.add(textServIP);

            this.gbc.gridx = 0;
            this.gbc.gridy = 2;
            this.gbl.setConstraints(checkOnlyWatch, gbc);
            this.add(checkOnlyWatch);
        }
    }
}
