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

import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Class responible for drawing Network Settings, when player wants to start
 * a game
 */
public class DrawNetworkSettings extends JPanel implements ActionListener {

    private JDialog parent;
    private GridBagLayout gbl;
    private GridBagConstraints gbc;
    private ButtonGroup serverORclient;
    private JRadioButton radioServer;
    private JRadioButton radioClient;
    private JLabel labelNick;
    private JLabel labelGameID;
    private JLabel labelOptions;
    private JPanel panelOptions;
    private JTextField textNick;
    private JTextField textGameID;
    private JButton buttonStart;
    private ClientOptionsPanel clientOptions;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Game.class);
    private URI uri;

    DrawNetworkSettings(JDialog parent) {
        super();

        //components
        this.parent = parent;

        this.radioServer = new JRadioButton("Spiel starten", true);
        this.radioClient = new JRadioButton("Mit Spiel verbinden", false);
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

        this.panelOptions.add(clientOptions);

        this.gbc.gridx = 0;
        this.gbc.gridy = 7;
        this.gbc.gridwidth = 2;
        this.gbl.setConstraints(buttonStart, gbc);
        this.add(buttonStart);
    }

    /**
     * Method for showing settings which the player is interested in
     */
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == this.radioServer) //show options for server
        {
            this.textGameID.setEnabled(false); // disable gameID
            this.panelOptions.removeAll();
            this.panelOptions.add(clientOptions);
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
            // validation
            if (this.textGameID.getText().isEmpty() && this.radioClient.isSelected()) {
                error = "Bitte geben Sie eine Game ID an.\n";
            }
            if (this.textNick.getText().length() == 0) {
                error += "Bitte geben Sie einen Nickname an.\n";
            }
            if (this.clientOptions.textServIP.getText().length() == 0) {
                error += "Bitte geben Sie eine IP an.\n";
            }

            // fixes CCD2015-59 [Client] Enable custom port
            try {
                // WORKAROUND: add any scheme to make the resulting URI valid.
                this.uri = new URI("my://" + this.clientOptions.textServIP.getText()); // may throw URISyntaxException

                if (uri.getHost() == null || uri.getPort() == -1) {
                    error += "Die URI muss aus einem Host und Port bestehen.\n";
                }

            } catch (URISyntaxException ex) {
                // validation failed
                logger.error("", ex);
            }

            if (error.length() > 0) {
                JOptionPane.showMessageDialog(this, error);
                return;
            }

            Game newGUI = JChessApp.getjChessView().addNewTab("Netzwerksspiel");

            // Start game on Server
            if (this.radioServer.isSelected()) {

                // Host game & join
                try {
                    newGUI.initiaizeAndJoinHostedGame(uri.getHost(), uri.getPort(), textNick.getText());

                    // wait for opponents
                    JOptionPane.showMessageDialog(this, "Ein Moment Geduld, wir warten noch auf Mitspieler!\nSpiel-ID: " + newGUI.getGameID());
                } catch (Exception e) {
                    logger.error("", e);
                }
            } else {

                // join existing game
                try {
                    // fixes: CCD2015-68 [Client] Wrong textbox
                    newGUI.joinGame(uri.getHost(), uri.getPort(), textGameID.getText(), textNick.getText());

                    // wait for opponents
                    JOptionPane.showMessageDialog(this, "Ein Moment Geduld, wir warten noch auf Mitspieler!");
                } catch (Exception e) {
                    logger.error("", e);
                }
            }

            // close network settings window
            this.parent.setVisible(false);
        }
    }

    /**
     *  Method responsible for drawing clients panel
     */
    private class ClientOptionsPanel extends JPanel //options for client
    {

        public JTextField textServIP;
        private GridBagLayout gbl;
        private GridBagConstraints gbc;
        private JLabel labelServIP;

        ClientOptionsPanel() {
            super();

            this.labelServIP = new JLabel("Server IP / Domain inkl. Port");
            this.textServIP = new JTextField();
            this.textServIP.setText("127.0.0.1:8080");

            this.gbl = new GridBagLayout();
            this.gbc = new GridBagConstraints();
            this.gbc.fill = GridBagConstraints.BOTH;
            this.setLayout(gbl);

            this.gbc.gridx = 0;
            this.gbc.gridy = 0;
            this.gbc.gridwidth = 2;
            this.gbl.setConstraints(labelServIP, gbc);
            this.add(labelServIP);

            this.gbc.gridx = 0;
            this.gbc.gridy = 1;
            this.gbc.gridwidth = 2;
            this.gbl.setConstraints(textServIP, gbc);
            this.add(textServIP);
        }
    }
}
