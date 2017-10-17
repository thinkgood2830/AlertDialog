package com.thinkgood.alertdialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.thinkgood.customalertdialog.CFAlertDialog;

public class MainActivity extends AppCompatActivity implements SampleFooterView.FooterActionListener {

    private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#B3000000");

    private EditText titleEditText, messageEditText;
    private CheckBox positiveButtonCheckbox, negativeButtonCheckbox, neutralButtonCheckbox, addHeaderCheckBox, addFooterCheckBox, closesOnBackgroundTapCheckBox;
    private RadioButton itemsRadioButton, multiChoiceRadioButton, singleChoiceRadioButton;
    private RadioButton textGravityLeft, textGravityCenter, textGravityRight;
    private RadioButton buttonGravityLeft, buttonGravityRight, buttonGravityCenter, buttonGravityFull;
    private CheckBox showTitleIcon;
    private RadioButton topDialogGravityRadioButton, centerDialogGravityRadioButton, bottomDialogGravityRadioButton;
    private View selectedBackgroundColorView, selectBackgroundColorContainer;
    private FloatingActionButton showDialogFab;
    private CFAlertDialog alertDialog;
    private CFAlertDialog colorSelectionDialog;
    private ColorSelectionView colorSelectionView;
    private boolean headerVisibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        showDialogFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCFDialog();
            }
        });
        selectBackgroundColorContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorSelectionAlert();
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void showColorSelectionAlert() {
        if (colorSelectionDialog == null) {

            colorSelectionView = new ColorSelectionView(this);
            colorSelectionView.setSelectedColor(DEFAULT_BACKGROUND_COLOR);
            colorSelectionDialog = new CFAlertDialog.Builder(this)
                    .addButton("Done", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Update the color preview
                            setSelectedBackgroundColor(colorSelectionView.selectedColor);

                            // dismiss the dialog
                            colorSelectionDialog.dismiss();
                        }
                    })
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                    .setHeaderView(colorSelectionView)
                    .onDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {

                            // Update the color preview
                            setSelectedBackgroundColor(colorSelectionView.selectedColor);
                        }
                    })
                    .create();
        }
        colorSelectionDialog.show();
    }

    private void showCFDialog() {

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);

        // Vertical position of the dialog
        if (topDialogGravityRadioButton.isChecked()) {
            builder.setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION);
        }
        if (centerDialogGravityRadioButton.isChecked()) {
            builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        }
        if (bottomDialogGravityRadioButton.isChecked()) {
            builder.setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET);
        }

        // Background
        int alertBGColor = -1;
        if (colorSelectionView != null) {
            alertBGColor = colorSelectionView.selectedColor;
            builder.setBackgroundColor(alertBGColor);
        }

        // Title and message
        builder.setTitle(titleEditText.getText());
        builder.setMessage(messageEditText.getText());

        if (textGravityLeft.isChecked()) {
            builder.setTextGravity(Gravity.START);
        } else if (textGravityCenter.isChecked()) {
            builder.setTextGravity(Gravity.CENTER_HORIZONTAL);
        } else if (textGravityRight.isChecked()) {
            builder.setTextGravity(Gravity.END);
        }

        // Title icon
        if (showTitleIcon.isChecked()) {
            builder.setIcon(R.drawable.icon_drawable);
        }

        // Buttons
        if (positiveButtonCheckbox.isChecked()) {

            // Add a sample positive button
            builder.addButton("Positive", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "Positive", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            });
        }
        if (negativeButtonCheckbox.isChecked()) {

            // Add a sample negative button
            builder.addButton("Negative", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, getButtonGravity(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "Negative", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            });
        }
        if (neutralButtonCheckbox.isChecked()) {

            // Add a sample neutral button
            builder.addButton("Neutral", -1, -1, CFAlertDialog.CFAlertActionStyle.DEFAULT, getButtonGravity(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "Neutral", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            });
        }

        // Add Header
        if (addHeaderCheckBox.isChecked()) {
            builder.setHeaderView(R.layout.dialog_header_layout);
            headerVisibility = true;
        }

        // Add Footer
        if (addFooterCheckBox.isChecked()) {
            SampleFooterView footerView = new SampleFooterView(this);
            footerView.setSelecteBackgroundColor(alertBGColor);
            builder.setFooterView(footerView);
        }

        // Selection Items
        if (itemsRadioButton.isChecked()) {

            // List items
            builder.setItems(new String[]{"First", "Second", "Third"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            Toast.makeText(getApplicationContext(), "First", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(getApplicationContext(), "Second", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(), "Third", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        } else if (singleChoiceRadioButton.isChecked()) {

            // Single choice list items
            builder.setSingleChoiceItems(new String[]{"First", "Second", "Third"}, 1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            Toast.makeText(getApplicationContext(), "First", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(getApplicationContext(), "Second", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(), "Third", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        } else if (multiChoiceRadioButton.isChecked()) {

            // Multi choice list items
            builder.setMultiChoiceItems(new String[]{"First", "Second", "Third"}, new boolean[]{true, false, false}, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    switch (which) {
                        case 0:
                            Toast.makeText(getApplicationContext(), "First " + (isChecked ? "Checked" : "Unchecked"), Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(getApplicationContext(), "Second " + (isChecked ? "Checked" : "Unchecked"), Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(), "Third " + (isChecked ? "Checked" : "Unchecked"), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

        }

        // Cancel on background tap
        builder.setCancelable(closesOnBackgroundTapCheckBox.isChecked());

        alertDialog = builder.show();
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                onDialogDismiss();
            }
        });
    }

    private CFAlertDialog.CFAlertActionAlignment getButtonGravity() {

        if (buttonGravityLeft.isChecked()) {
            return CFAlertDialog.CFAlertActionAlignment.START;
        }
        if (buttonGravityCenter.isChecked()) {
            return CFAlertDialog.CFAlertActionAlignment.CENTER;
        }
        if (buttonGravityRight.isChecked()) {
            return CFAlertDialog.CFAlertActionAlignment.END;
        }
        if (buttonGravityFull.isChecked()) {
            return CFAlertDialog.CFAlertActionAlignment.JUSTIFIED;
        }
        return CFAlertDialog.CFAlertActionAlignment.JUSTIFIED;
    }

    private void bindViews() {
        titleEditText = (EditText) findViewById(R.id.title_edittext);
        messageEditText = (EditText) findViewById(R.id.message_edittext);

        textGravityLeft = (RadioButton) findViewById(R.id.text_gravity_left);
        textGravityCenter = (RadioButton) findViewById(R.id.text_gravity_center);
        textGravityRight = (RadioButton) findViewById(R.id.text_gravity_right);

        positiveButtonCheckbox = (CheckBox) findViewById(R.id.positive_button_checkbox);
        negativeButtonCheckbox = (CheckBox) findViewById(R.id.negative_button_checkbox);
        neutralButtonCheckbox = (CheckBox) findViewById(R.id.neutral_button_checkbox);

        addHeaderCheckBox = (CheckBox) findViewById(R.id.add_header_checkbox);
        addFooterCheckBox = (CheckBox) findViewById(R.id.add_footer_checkbox);

        buttonGravityLeft = (RadioButton) findViewById(R.id.button_gravity_left);
        buttonGravityCenter = (RadioButton) findViewById(R.id.button_gravity_center);
        buttonGravityRight = (RadioButton) findViewById(R.id.button_gravity_right);
        buttonGravityFull = (RadioButton) findViewById(R.id.button_gravity_justified);

        itemsRadioButton = (RadioButton) findViewById(R.id.items_radio_button);
        multiChoiceRadioButton = (RadioButton) findViewById(R.id.multi_select_choice_items_radio_button);
        singleChoiceRadioButton = (RadioButton) findViewById(R.id.single_choice_items_radio_button);

        showTitleIcon = (CheckBox) findViewById(R.id.show_title_icon);

        topDialogGravityRadioButton = (RadioButton) findViewById(R.id.top_dialog_gravity_radio_button);
        centerDialogGravityRadioButton = (RadioButton) findViewById(R.id.center_dialog_gravity_radio_button);
        bottomDialogGravityRadioButton = (RadioButton) findViewById(R.id.bottom_dialog_gravity_radio_button);

        closesOnBackgroundTapCheckBox = ((CheckBox) findViewById(R.id.closes_on_background_tap));
        selectedBackgroundColorView = findViewById(R.id.background_color_preview);
        setSelectedBackgroundColor(DEFAULT_BACKGROUND_COLOR);
        selectBackgroundColorContainer = findViewById(R.id.background_color_selection_container);

        showDialogFab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void setSelectedBackgroundColor(int color) {
        GradientDrawable previewBackground = (GradientDrawable) selectedBackgroundColorView.getBackground();
        previewBackground.setColor(color);
        ViewCompat.setBackground(selectedBackgroundColorView, previewBackground);
    }

    @Override
    public void onBackgroundColorChanged(int backgroundColor) {
        alertDialog.setBackgroundColor(backgroundColor, true);
    }

    @Override
    public void onHeaderAdded() {
        if (alertDialog != null) { alertDialog.setHeaderView(R.layout.dialog_header_layout); }
        headerVisibility = true;

    }

    @Override
    public void onHeaderRemoved() {
        if (alertDialog != null) { alertDialog.setHeaderView(null); }
        headerVisibility = false;
    }

    private void onDialogDismiss() {
        headerVisibility = false;
    }

    @Override
    public boolean isHeaderVisible() {
        return headerVisibility;
    }


}
