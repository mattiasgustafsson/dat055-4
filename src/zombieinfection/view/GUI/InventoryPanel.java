package zombieinfection.view.GUI;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import zombieinfection.controller.InventoryController;

public class InventoryPanel extends JPanel implements PropertyChangeListener {
    private JPanel food;
    private JPanel ingredients;
    private JPanel weapons;
	private JPanel other;
	private JButton[] fbuttons;
	private JLabel[] iLabels;
	private JLabel[] wLabels;
	private JLabel[] oLabels;
	private int noOfIngredients, noOfFoodItems, noOfWeapons, noOfOther;
	private JLabel label;
	
	private static final long serialVersionUID = 4112124953987216988L;

	public InventoryPanel(InventoryController ic) {
		this.setLayout(new GridLayout(4, 1));
		
		// Food section
        GridBagConstraints cFood = new GridBagConstraints();
        food = new JPanel(new GridBagLayout());
        label = new JLabel("Food:");
        cFood.anchor = GridBagConstraints.WEST;
        food.add(label, cFood);
        fbuttons = new JButton[4];
        cFood.gridy = 1;
        cFood.weightx = 1;
        cFood.ipady = 15;
        cFood.fill = GridBagConstraints.HORIZONTAL;
        for (int i = 0; i < 4; i++) { // Add food buttons to GUI
            fbuttons[i] = new JButton("");
            cFood.gridx = i;
            food.add(fbuttons[i], cFood);
        }
        // Add listeners to food buttons
        fbuttons[0].addActionListener(e -> {ic.foodSlotClicked(fbuttons[0].getText());});
        fbuttons[1].addActionListener(e -> {ic.foodSlotClicked(fbuttons[1].getText());});
        fbuttons[2].addActionListener(e -> {ic.foodSlotClicked(fbuttons[2].getText());});
        fbuttons[3].addActionListener(e -> {ic.foodSlotClicked(fbuttons[3].getText());});
        noOfFoodItems = 0;
	    
	    // Ingredient section
	    ingredients = new JPanel(new GridBagLayout());
	    GridBagConstraints cIngredient = new GridBagConstraints();
        label = new JLabel("Ingredients:");
        cIngredient.gridx = 0;
        cIngredient.gridy = 0;
        cIngredient.anchor = GridBagConstraints.WEST;
        ingredients.add(label, cIngredient);
        cIngredient.gridy = 1;
        cIngredient.weightx = 1;
        cIngredient.ipady = 15;
        cIngredient.fill = GridBagConstraints.HORIZONTAL;
        iLabels = new JLabel[4]; // Add ingredient labels to GUI
        for (int i = 0; i < 4; i++) {
            iLabels[i] = new JLabel("");
            cIngredient.gridx = i;
            ingredients.add(iLabels[i], cIngredient);
        }
        noOfIngredients = 0;
        
        // Weapon section
        weapons = new JPanel(new GridBagLayout());
        GridBagConstraints cWeapon = new GridBagConstraints();
        label = new JLabel("Weapons:");
        cWeapon.gridx = 0;
        cWeapon.gridy = 0;
        cWeapon.anchor = GridBagConstraints.WEST;
        weapons.add(label, cWeapon);
        cWeapon.gridy = 1;
        cWeapon.weightx = 1;
        cWeapon.ipady = 15;
        cWeapon.fill = GridBagConstraints.HORIZONTAL;
        wLabels = new JLabel[4]; // Add weapon labels to GUI
        for (int i = 0; i < 4; i++) {
            wLabels[i] = new JLabel("");
            cWeapon.gridx = i;
            weapons.add(wLabels[i], cWeapon);
        }
        noOfWeapons = 0;
        
        // Other section
        other = new JPanel(new GridBagLayout());
        GridBagConstraints cOther = new GridBagConstraints();
        label = new JLabel("Other:");
        cOther.gridx = 0;
        cOther.gridy = 0;
        cOther.anchor = GridBagConstraints.WEST;
        other.add(label, cOther);
        cOther.gridy = 1;
        cOther.weightx = 1;
        cOther.ipady = 15;
        cOther.fill = GridBagConstraints.HORIZONTAL;
        oLabels = new JLabel[4]; // Add "other" labels to GUI
        for (int i = 0; i < 4; i++) {
            oLabels[i] = new JLabel("");
            cOther.gridx = i;
            other.add(oLabels[i], cOther);
        }
        noOfOther = 0;

        this.add(food);
        this.add(ingredients);
        this.add(weapons);
        this.add(other);
	}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    	JFrame frameRef = (JFrame) SwingUtilities.windowForComponent(this);
    	frameRef.requestFocusInWindow();
    	
    	String propName = evt.getPropertyName();
        Object o = evt.getNewValue();
        if (propName.equals("foodPicked")) {
            String fName = o.toString();
            fbuttons[noOfFoodItems++].setText(fName);
        }
        else if (propName.equals("foodEaten")) {
            String fName = o.toString();
            for (int i = 0; i < fbuttons.length; i++) {
                if (fbuttons[i].getText().equals(fName)) {
                    fbuttons[i].setText("");
                }
            }
            noOfFoodItems--;
        }
        else if (propName.equals("ingredientPicked")) {
            String ingName = o.toString();
            iLabels[noOfIngredients++].setText(ingName);
        }
        else if (propName.equals("inventory")) {
            for (int i = 0; i < 4; i++) {
                iLabels[i].setText("");
            }
        }
        else if (propName.equals("recipePicked")) {
                oLabels[noOfOther++].setText(o.toString());
        }
        else if (propName.equals("weaponPicked")) {
        	String wName = o.toString();
            wLabels[noOfWeapons++].setText(wName);
        }
        
    }	
}
