package es.iescarrillo.calculadora_jml;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private String currentInput = "";
    private String operator = "";
    private double firstNumber = 0;
    private double secondNumber = 0;
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textViewResult = findViewById(R.id.textViewResult);


        setNumberButtonListener(findViewById(R.id.button0), "0");
        setNumberButtonListener(findViewById(R.id.button1), "1");
        setNumberButtonListener(findViewById(R.id.button2), "2");
        setNumberButtonListener(findViewById(R.id.button3), "3");
        setNumberButtonListener(findViewById(R.id.button4), "4");
        setNumberButtonListener(findViewById(R.id.button5), "5");
        setNumberButtonListener(findViewById(R.id.button6), "6");
        setNumberButtonListener(findViewById(R.id.button7), "7");
        setNumberButtonListener(findViewById(R.id.button8), "8");
        setNumberButtonListener(findViewById(R.id.button9), "9");
        setNumberButtonListener(findViewById(R.id.buttonDecimal), ".");


        setOperatorButtonListener(findViewById(R.id.buttonPlus), "+");
        setOperatorButtonListener(findViewById(R.id.buttonMinus), "-");
        setOperatorButtonListener(findViewById(R.id.buttonMultiply), "*");
        setOperatorButtonListener(findViewById(R.id.buttonDivide), "/");


        Button buttonEquals = findViewById(R.id.buttonEquals);
        buttonEquals.setOnClickListener(v -> onEqualPressed());


        Button buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(v -> onClearPressed());

    }


    private void setNumberButtonListener(Button button, String value) {
        button.setOnClickListener(v -> {
            if (isNewOperation) {
                currentInput = "";
                isNewOperation = false;
            }
            currentInput += value;
            textViewResult.setText(currentInput);
        });
    }

    
    private void setOperatorButtonListener(Button button, String selectedOperator) {
        button.setOnClickListener(v -> {
            if (!currentInput.isEmpty() && firstNumber!=0){ //frankenstein con la función del igual para dar la funcionalidad de que se puedan encadenar operadores y numeros, y ver su resultado sin pulsar igual.
                secondNumber = Double.parseDouble(currentInput);
                double result = 0;

                if (selectedOperator.equals("+")){
                    result = firstNumber + secondNumber;
                } else if (selectedOperator.equals("-")) {
                    result = firstNumber - secondNumber;
                } else if (selectedOperator.equals("*")){
                    result = firstNumber * secondNumber;
                } else if (selectedOperator.equals("/")){
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        textViewResult.setText("Error");
                        secondNumber = 0;
                        currentInput = "";
                        isNewOperation = true;
                        return;
                    }
                }



                textViewResult.setText(String.valueOf(result));
                firstNumber = result;
                secondNumber = 0;
                isNewOperation = true;
                operator = selectedOperator;
                currentInput = "";
            }
            if (!currentInput.isEmpty()) {
                firstNumber = Double.parseDouble(currentInput);
                operator = selectedOperator;
                currentInput = "";
                textViewResult.setText("");
            }
        });
    }


    private void onEqualPressed() {
        if (!currentInput.isEmpty()) {
            secondNumber = Double.parseDouble(currentInput);
            double result = 0;

            if (operator.equals("+")){
                result = firstNumber + secondNumber;
            } else if (operator.equals("-")) {
                result = firstNumber - secondNumber;
            } else if (operator.equals("*")){
                result = firstNumber * secondNumber;
            } else if (operator.equals("/")){
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    textViewResult.setText("Error");
                    return;
                }
            }



            textViewResult.setText(String.valueOf(result));
            currentInput = String.valueOf(result);
            isNewOperation = true;
            secondNumber = 0;
        }
    }


    private void onClearPressed() {
        currentInput = "";
        firstNumber = 0;
        operator = "";
        isNewOperation = true;
        textViewResult.setText("0");
    }
}