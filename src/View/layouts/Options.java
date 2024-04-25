package View.layouts;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import utils.Util;
import View.listeners.EventListener;

public class Options extends JPanel{

    private static final long serialVersionUID = 1L;

    private JButton jbAdd;
    private JButton jbEdit;
    private JButton jbDetails;
    private JButton jbRemove;

    private JToolBar jtbOptions;

    private EventListener eventListener;

    public Options(EventListener eventListener) {
        this.eventListener = eventListener;

        //Flowlayout is the default layout of panel
        setLayout(new BorderLayout());
        configure();
        registerListeners();
    }

    private void configure(){
        jtbOptions = new JToolBar("Barre des tâches");

        jbAdd = createButton("Adicionar", "add_obj");
        jbEdit = createButton("Editar", "edit");
        jbDetails = createButton("Editar", "details");
        jbRemove = createButton("Editar", "remove");

        this.add(jtbOptions, BorderLayout.CENTER);
    }

    private JButton createButton(String hint, String icon){
        JButton jButton = new JButton();

        jButton.setIcon(Util.getIcon(getClass(), icon));
        jButton.setToolTipText(hint);
        jtbOptions.add(jButton);

        return jButton;
    }

    private void registerListeners(){
        jbEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                eventListener.cmdEdit();
            }
        });

        jbAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                eventListener.cmdAdd();
            }
        });

        jbRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                eventListener.cmdRemove();
            }
        });
    }
}
