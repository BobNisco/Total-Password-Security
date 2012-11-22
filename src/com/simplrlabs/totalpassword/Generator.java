package com.simplrlabs.totalpassword;

import java.util.prefs.InvalidPreferencesFormatException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.SherlockFragment;

public class Generator extends SherlockFragment
{
	String newPassword = "";
    PasswordGenerator pg = new PasswordGenerator();
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generator, container, false);
        
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                view.getContext(), R.array.length, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        CheckBox lowercaseCheck2 = (CheckBox) view.findViewById(R.id.lowercase);
        lowercaseCheck2.setChecked(true);
        
        // Create EditText field for password to go into
        final EditText generated = (EditText) view.findViewById(R.id.generated_password);
        
        // Button to generate new password
        final Button button = (Button) view.findViewById(R.id.generate_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	pg.generatePassword();
            	final String password = pg.getPassword().toString();
				generated.setText(password);
				newPassword = password;
            }
        });
        
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        
        final CheckBox lowercaseCheck = (CheckBox) view.findViewById(R.id.lowercase);
        lowercaseCheck.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    try {
						pg.setLowercaseIncluded(true);
					} catch (InvalidPreferencesFormatException e) {
						e.printStackTrace();
					}
                } 
                else {
                	try {
						pg.setLowercaseIncluded(false);
					} catch (InvalidPreferencesFormatException e) {
						e.printStackTrace();
					}
                }
            }
        });
        
        final CheckBox uppercaseCheck = (CheckBox) view.findViewById(R.id.uppercase);
        uppercaseCheck.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    try {
						pg.setUppercaseIncluded(true);
					} catch (InvalidPreferencesFormatException e) {
						e.printStackTrace();
					}
                } 
                else {
                	try {
						pg.setUppercaseIncluded(false);
					} catch (InvalidPreferencesFormatException e) {
						e.printStackTrace();
					}
                }
            }
        });
        
        final CheckBox numbersCheck = (CheckBox) view.findViewById(R.id.numbers);
        numbersCheck.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked())  {
                    try {
						pg.setNumbersIncluded(true);
					} catch (InvalidPreferencesFormatException e) {
						e.printStackTrace();
					}
                } 
                else {
                	try {
						pg.setNumbersIncluded(false);
					} catch (InvalidPreferencesFormatException e) {
						e.printStackTrace();
					}
                }
            }
        });
        
        final CheckBox specialCheck = (CheckBox) view.findViewById(R.id.special);
        specialCheck.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) 
                {
                    try {
						pg.setOthersIncluded(true);
					} catch (InvalidPreferencesFormatException e) {
						e.printStackTrace();
					}
                } 
                else 
                {
                	try {
						pg.setOthersIncluded(false);
					} catch (InvalidPreferencesFormatException e) {
						e.printStackTrace();
					}
                }
            }
        });
        
        return view;
	}
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	    	String rawdata;
	    	int newLength;
	    	rawdata = parent.getItemAtPosition(pos).toString();
	    	newLength = Integer.parseInt(rawdata);
	    	pg.setLength(newLength);
	    }

	    public void onNothingSelected(AdapterView<?> parent) {
	      // Do nothing.
	    }
	}
}
