import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.SwingUtilities;
//import javax.swing.filechooser.*;

public class IzbiraDatoteke extends JPanel implements ActionListener
{
    static private final String newline = "\n"; // statièna spremenljivka, ki kot vrednost dobi znak za novo vrstico

    private JButton openButton;
    private JButton saveButton;
    private JTextArea log;
    private JFileChooser fc;

    public IzbiraDatoteke()
    {
        super(new BorderLayout());

        // Ustvarimo besedilno polje za zapisovanje dogodkov
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5)); // Odmik od robov
        log.setEditable(false); // Onemogoèimo urejanje besedila
        JScrollPane logScrollPane = new JScrollPane(log);

        // Ustvarimo objekt za izbiro datotek ali map, parameter je zaèetna mapa
        fc = new JFileChooser("c:/java");

        // Tri možnosti prikaza: le mape, mape in datoteke ali le datoteke,
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Ustvarimo gumb za odpiranje
        openButton = new JButton("Odpri datoteko");
        openButton.addActionListener(this);


        // Ustvarimo gumb za shranjevanje
        saveButton = new JButton("Shrani");
        saveButton.addActionListener(this);

        // Postavi gumba na posebno plošèo
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);

        // Dodaj plošèi na glavno plošèo
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e)
    {
        // Postopek za klik na gumb Odpri
        if (e.getSource() == openButton)
        {
			// Pokaži okno za odpiranje/branje datotek
            int returnVal = fc.showOpenDialog(this);

 			// Èe odpiranje potrdimo, ustvarimo željeno datoteko
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File file = fc.getSelectedFile();
                // Zapišimo, kaj smo izbrali
                log.append("Odpiram: " + file.getParent() + "/" + file.getName() + "." + newline);
            }
            else
            {
                log.append("Uporabnik je preklical odpiranje." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());

        // Ob kliku na gumb Shrani preberemo ime datoteke, v katero shranjujemo
        }
        else if (e.getSource() == saveButton)
        {
			// Odpri okno za shranjevanje datotek
            int returnVal = fc.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File file = fc.getSelectedFile();
                // Zapišimo, kaj smo izbrali
                log.append("Shranjujem: " + file.getParent() + "/" + file.getName() + "." + newline);
            }
            else
            {
                log.append("Uporabnik je preklical shranjevanje." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        }
    }

    public static void main(String[] args)
    {
		JFrame frame = new JFrame("Izberi datoteko");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(new IzbiraDatoteke()); // Dodamo plošèo z gumboma Odpri in Shrani ter tekstovnim poljem.
		frame.pack();
        frame.setVisible(true);
    }
}
