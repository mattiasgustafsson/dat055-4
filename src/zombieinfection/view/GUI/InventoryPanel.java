package zombieinfection.view.GUI;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import zombieinfection.controller.InventoryController;

/**
 * This class is used to show the contents of the player's inventory in the GUI.
 * 
 * @author Mattias Gustafsson
 * @version 2018-02-22
 *
 */
public class InventoryPanel extends JPanel implements PropertyChangeListener {
	private JPanel food;
	private JPanel ingredients;
	private JPanel armours;
	private JPanel other;
	private JButton[] fbuttons;
	private JLabel[] iLabels;
	private JLabel[] aLabels;
	private JLabel[] oLabels;
	private int noOfIngredients, noOfArmours, noOfOther;
	private JLabel label;
	private static final long serialVersionUID = 4112124953987216988L;

	/**
	 * Creates an object of the InventoryPanel class.
	 * 
	 * @param ic
	 *            the controller object that handles the communication between this
	 *            object an the model object
	 */
	public InventoryPanel(InventoryController ic) {
		this.setLayout(new GridLayout(4, 1));
		Font bold = new Font("Dialog", Font.BOLD, 15);

		// Food section
		GridBagConstraints cFood = new GridBagConstraints();
		food = new JPanel(new GridBagLayout());
		label = new JLabel("Food:");
		label.setFont(bold);
		cFood.anchor = GridBagConstraints.WEST;
		food.add(label, cFood);
		fbuttons = new JButton[4];
		cFood.gridy = 1;
		cFood.weightx = 1;
		cFood.ipady = 15;
		cFood.fill = GridBagConstraints.HORIZONTAL;
		for (int i = 0; i < 4; i++) { // Add food buttons to GUI
			JButton temp = new JButton("");
			temp.setFocusable(false);
			fbuttons[i] = temp;
			cFood.gridx = i;
			food.add(fbuttons[i], cFood);
		}
		// Add listeners to food buttons
		fbuttons[0].addActionListener(e -> {
			ic.foodSlotClicked(fbuttons[0].getText());
		});
		fbuttons[1].addActionListener(e -> {
			ic.foodSlotClicked(fbuttons[1].getText());
		});
		fbuttons[2].addActionListener(e -> {
			ic.foodSlotClicked(fbuttons[2].getText());
		});
		fbuttons[3].addActionListener(e -> {
			ic.foodSlotClicked(fbuttons[3].getText());
		});

		// Ingredient section
		ingredients = new JPanel(new GridBagLayout());
		GridBagConstraints cIngredient = new GridBagConstraints();
		label = new JLabel("Ingredients:");
		label.setFont(bold);
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

		// Armour section
		armours = new JPanel(new GridBagLayout());
		GridBagConstraints cArmour = new GridBagConstraints();
		label = new JLabel("Armour:");
		label.setFont(bold);
		cArmour.gridx = 0;
		cArmour.gridy = 0;
		cArmour.anchor = GridBagConstraints.WEST;
		armours.add(label, cArmour);
		cArmour.gridy = 1;
		cArmour.weightx = 1;
		cArmour.ipady = 15;
		cArmour.fill = GridBagConstraints.HORIZONTAL;
		aLabels = new JLabel[4];
		for (int i = 0; i < 4; i++) {
			aLabels[i] = new JLabel("");
			cArmour.gridx = i;
			armours.add(aLabels[i], cArmour);
		}
		noOfArmours = 0;

		// Other section
		other = new JPanel(new GridBagLayout());
		GridBagConstraints cOther = new GridBagConstraints();
		label = new JLabel("Other:");
		label.setFont(bold);
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
		this.add(armours);
		this.add(other);
	}

	/**
	 * Update inventory part of GUI when values changes in the corresponding parts
	 * of the model.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		JFrame frameRef = (JFrame) SwingUtilities.windowForComponent(this);
		frameRef.requestFocusInWindow();

		String propName = evt.getPropertyName();
		Object o = evt.getNewValue();
		if (propName.equals("foodPicked")) {
			String fName = o.toString();
			for (JButton b : fbuttons) {
				if (b.getText().equals("")) {
					b.setText(fName);
					return;
				}
			}
		} else if (propName.equals("foodEaten")) {
			String fName = o.toString();
			for (int i = 0; i < fbuttons.length; i++) {
				if (fbuttons[i].getText().equals(fName)) {
					fbuttons[i].setText("");
				}
			}
		} else if (propName.equals("ingredientPicked")) {
			String ingName = o.toString();
			iLabels[noOfIngredients++].setText(ingName);
		} else if (propName.equals("inventory")) {
			for (int i = 0; i < 4; i++) {
				iLabels[i].setText("");
			}
		} else if (propName.equals("recipePicked")) {
			oLabels[noOfOther++].setText(o.toString());
		} else if (propName.equals("keyPicked")) {
			oLabels[noOfOther++].setText(o.toString());
		} else if (propName.equals("armourPicked")) {
			String wName = o.toString();
			aLabels[noOfArmours++].setText(wName);
		} else if (propName.equals("inventoryCleared")) {
			for (int i = 0; i < 4; i++) {
				fbuttons[i].setText("");
				iLabels[i].setText("");
				aLabels[i].setText("");
				oLabels[i].setText("");
			}
			noOfIngredients = 0;
			noOfArmours = 0;
			noOfOther = 0;
		}
	}
}
