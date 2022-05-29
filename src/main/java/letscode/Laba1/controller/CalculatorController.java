
package letscode.Laba1.controller;

import letscode.Laba1.entity.SolutionParameters;
import letscode.Laba1.enums.CharacteristicType;
import letscode.Laba1.exception.ActionNotValidException;
import letscode.Laba1.logger.MyLogger;
import letscode.Laba1.service.CalculatorService;
import letscode.Laba1.service.RequestCounter;
import letscode.Laba1.util.Action;
import org.apache.logging.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

@RestController
public class CalculatorController {

    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);

    @Autowired
    CalculatorService calculatorService;

    @GetMapping("/calculate/{action}")
    @ResponseBody
    public SolutionParameters calculatorJSON(@PathVariable String action,
                                             @RequestParam(value = "solution", required = false, defaultValue = "0.0") double solution,
                                             @RequestParam(value = "substance", required = false, defaultValue = "0.0") double substance,
                                             @RequestParam(value = "percent", required = true, defaultValue = "0.0") double percent)
            throws ActionNotValidException{
        if (percent < 0.0 || percent > 100.0) {
            throw new ActionNotValidException("Percentage is incorrect");
        }
        else if (action.equals(Action.CALCULATE_SUBSTANCE)) {
            RequestCounter.increase();
            return new SolutionParameters(solution, percent, calculatorService.calculateDrySubstanceMass(percent, solution));

        } else if (action.equals(Action.CALCULATE_SOLUTION)) {
            RequestCounter.increase();
            return new SolutionParameters(calculatorService.calculateSolutionMass(percent, substance), percent, substance);
        } else {
            throw new ActionNotValidException("Request is incorrect");
        }
    }
    @PostMapping({"/calculate"})
    public ResponseEntity<Object> calculateBulk(@RequestBody @Validated List<SolutionParameters> inputList) {
        List<SolutionParameters> outputList = new LinkedList();
        inputList.forEach((inputParams) -> {
            try {
                outputList.add(this.calculatorService.calculate(inputParams, CharacteristicType.solution_substance));
                MyLogger.log(Level.INFO, "Output list - " + outputList);
            } catch (IllegalArgumentException var4) {
                MyLogger.log(Level.ERROR, "POST error!");
            }

        });
        MyLogger.log(Level.ERROR, "POST success!");
        DecimalFormat dec = new DecimalFormat("#0.00");
        double solutionAverage = this.calculatorService.calculateAverage(inputList, CharacteristicType.solution);
        double substanceAverage = this.calculatorService.calculateAverage(inputList, CharacteristicType.substance);
        double percentAverage = this.calculatorService.calculateAverage(outputList, CharacteristicType.percent);
        double solutionMin = this.calculatorService.findMin(inputList, CharacteristicType.solution);
        double substanceMin = this.calculatorService.findMin(inputList, CharacteristicType.substance);
        double percentMin = this.calculatorService.findMin(outputList, CharacteristicType.percent);
        double solutionMax = this.calculatorService.findMax(inputList, CharacteristicType.solution);
        double substanceMax = this.calculatorService.findMax(inputList, CharacteristicType.substance);
        double percentMax = this.calculatorService.findMax(outputList, CharacteristicType.percent);
        String var10002 = dec.format(solutionAverage);
        return new ResponseEntity("Solution average - " + var10002 + "; substance average - " + dec.format(substanceAverage) + "; percent average - " + dec.format(percentAverage)  + ".\nSolution min - " + dec.format(solutionMin) + "; substance min - " + dec.format(substanceMin) + "; percent min - " + dec.format(percentMin) + ".\nSolution max - " + dec.format(solutionMax) + "; substance max - " + dec.format(substanceMax) + "; percent max - " + dec.format(percentMax) + ".\n", HttpStatus.OK);
    }

}