package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final char ADDITION = '+';
    private static final char SUBTRACTION = '-';
    private static final char MULTIPLICATION = '*';
    private static final char DIVISION = '/';
    private static final char PERCENT = '%';

    private char currentSymbol;
    private double firstValue = Double.NaN;
    private double secondValue;
    private TextView inputDisplay, outputDisplay;
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        decimalFormat = new DecimalFormat("#.##########");
        inputDisplay = findViewById(R.id.input);
        outputDisplay = findViewById(R.id.output);

        initializeButtons();
        setOperatorButtonListeners();
    }

    private void initializeButtons() {
        findViewById(R.id.btn0).setOnClickListener(numberClickListener);
        findViewById(R.id.btn1).setOnClickListener(numberClickListener);
        findViewById(R.id.btn2).setOnClickListener(numberClickListener);
        findViewById(R.id.btn3).setOnClickListener(numberClickListener);
        findViewById(R.id.btn4).setOnClickListener(numberClickListener);
        findViewById(R.id.btn5).setOnClickListener(numberClickListener);
        findViewById(R.id.btn6).setOnClickListener(numberClickListener);
        findViewById(R.id.btn7).setOnClickListener(numberClickListener);
        findViewById(R.id.btn8).setOnClickListener(numberClickListener);
        findViewById(R.id.btn9).setOnClickListener(numberClickListener);
    }

    private final View.OnClickListener numberClickListener = view -> {
        MaterialButton button = (MaterialButton) view;
        inputDisplay.append(button.getText());
    };

    private void setOperatorButtonListeners() {
        findViewById(R.id.add).setOnClickListener(operatorClickListener);
        findViewById(R.id.subtract).setOnClickListener(operatorClickListener);
        findViewById(R.id.multiply).setOnClickListener(operatorClickListener);
        findViewById(R.id.division).setOnClickListener(operatorClickListener);
        findViewById(R.id.percent).setOnClickListener(operatorClickListener);

        findViewById(R.id.btnPoint).setOnClickListener(view -> inputDisplay.append("."));

        findViewById(R.id.clear).setOnClickListener(view -> {
            inputDisplay.setText("");
            outputDisplay.setText("");
            firstValue = Double.NaN;
            secondValue = Double.NaN;
        });

        findViewById(R.id.off).setOnClickListener(view -> finish());

        findViewById(R.id.equal).setOnClickListener(view -> {
            allCalculations();
            outputDisplay.setText(decimalFormat.format(firstValue));
            firstValue = Double.NaN;
            currentSymbol = '0';
        });
    }

    private final View.OnClickListener operatorClickListener = view -> {
        allCalculations();
        MaterialButton button = (MaterialButton) view;
        currentSymbol = button.getText().charAt(0);
        outputDisplay.setText(decimalFormat.format(firstValue) + " " + currentSymbol);
        inputDisplay.setText(null);
    };

    private void allCalculations() {
        if (!Double.isNaN(firstValue)) {
            try {
                secondValue = Double.parseDouble(inputDisplay.getText().toString());
            } catch (NumberFormatException e) {
                return; // If no valid number, exit
            }

            switch (currentSymbol) {
                case ADDITION:
                    firstValue += secondValue;
                    break;
                case SUBTRACTION:
                    firstValue -= secondValue;
                    break;
                case MULTIPLICATION:
                    firstValue *= secondValue;
                    break;
                case DIVISION:
                    if (secondValue != 0) {
                        firstValue /= secondValue;
                    } else {
                        outputDisplay.setText("Error");
                        return;
                    }
                    break;
                case PERCENT:
                    firstValue = (firstValue * secondValue) / 100;
                    break;
            }
        } else {
            try {
                firstValue = Double.parseDouble(inputDisplay.getText().toString());
            } catch (NumberFormatException e) {
                firstValue = Double.NaN;
            }
        }
    }
}
