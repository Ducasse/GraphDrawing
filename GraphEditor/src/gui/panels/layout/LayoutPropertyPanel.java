package gui.panels.layout;

import graph.layout.GraphLayoutProperties;
import graph.layout.PropertyEnums;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class LayoutPropertyPanel extends JPanel	{

	private static final long serialVersionUID = 1L;
	protected Map<Object, Component> componentsMap  = new HashMap<Object, Component>();

	public LayoutPropertyPanel (Class<?> enumClass){

		setLayout(new MigLayout());

		//analyze enum and generate panel
		Object[] consts = enumClass.getEnumConstants();
		Method m = null;
		Method hidden = null;
		try {
			m = enumClass.getMethod("getName");
			hidden = enumClass.getMethod("isHidden");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		for (Object enumC : consts){

			try{
				if (hidden != null && (boolean) hidden.invoke(enumC))
					continue;

				if (m == null)
					add(new JLabel(enumC.toString()));
				else
					add(new JLabel((String) m.invoke(enumC) + ":"));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			JTextField tf = new JTextField(10);
			add(tf, "wrap");
			componentsMap.put(enumC, tf);
		}
	}

	public void setDefaultValue(GraphLayoutProperties properties){
		if (properties == null)
			return;
		for (Object key : componentsMap.keySet()){
			if (properties.getProperty((PropertyEnums) key) != null)
				if (componentsMap.get(key) instanceof JTextField)
					((JTextField)componentsMap.get(key)).setText(properties.getProperty((PropertyEnums) key).toString());
		}
	}

	public GraphLayoutProperties getEnteredLayoutProperties(){
		GraphLayoutProperties layoutProperties = new GraphLayoutProperties();
		for (Object key : componentsMap.keySet()){

			if (componentsMap.get(key) instanceof JComboBox<?>)
				layoutProperties.setProperty((PropertyEnums)key, ((JComboBox<?>)componentsMap.get(key)).getSelectedItem());
			else if (componentsMap.get(key) instanceof JTextField){

				String content = ((JTextField)componentsMap.get(key)).getText();
				Double doubleValue = null;
				try{
					doubleValue = Double.parseDouble(content);
					layoutProperties.setProperty((PropertyEnums) key, doubleValue);
				}
				catch(Exception ex){
					if (!content.equals(""))
						layoutProperties.setProperty((PropertyEnums)key, content);
				}
			}


		}
		return layoutProperties;
	}


}