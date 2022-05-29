package letscode.Laba1.service;

import letscode.Laba1.entity.SolutionParameters;
import letscode.Laba1.enums.CharacteristicType;
import letscode.Laba1.logger.MyLogger;
import logic.CacheService;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculatorService {
    //@Autowired
    private double solution = 0.0;
    private double substance = 0.0;
    private double percent = 0.0;
    private CacheService cacheService = new CacheService();

    private SolutionParameters solutionParameters = new SolutionParameters();

    public double calculateSolutionMass(double percent, double substance) {
        solutionParameters.setSubstance(substance);
        solutionParameters.setSolution(substance / percent * 100.0);
        if (solutionParameters.getSolution() > 0 && solutionParameters.getSubstance() > 0) {
            MyLogger.log(Level.INFO, "Calculating solution mass...");
            cacheService.addToMap(solutionParameters, percent);
        }
        return substance / percent * 100.0;
    }

    public double calculateDrySubstanceMass(double percent, double solution) {
        solutionParameters.setSolution(solution);
        solutionParameters.setSubstance(solution / 100.0 * percent);
        if (solutionParameters.getSolution() > 0 && solutionParameters.getSubstance() > 0) {
            MyLogger.log(Level.INFO, "Calculating substance mass...");
            cacheService.addToMap(solutionParameters, percent);
        }
        return solution / 100.0 * percent;
    }

    public SolutionParameters calculate(SolutionParameters inputParams, CharacteristicType chrType) {
        if (chrType == CharacteristicType.solution || chrType == CharacteristicType.solution_substance) {
            MyLogger.log(Level.INFO, "Calculating solution...");
            this.solution = this.calculateSolutionMass(inputParams.getPercent(), inputParams.getSubstance());
        }

        if (chrType == CharacteristicType.substance || chrType == CharacteristicType.solution_substance) {
            MyLogger.log(Level.INFO, "Calculating substance...");
            this.substance = this.calculateSolutionMass(inputParams.getPercent(), inputParams.getSolution());
        }

        if (this.solution > 0.0 && this.substance > 0.0) {
            MyLogger.log(Level.INFO, "Adding params to cache...");
            SolutionParameters outputParams = new SolutionParameters(this.solution, this.substance, this.percent);
            this.cacheService.addToMap(inputParams, inputParams.getPercent());
            this.substance = this.solution = 0.0;
            return outputParams;
        } else {
            return null;
        }
    }


    public <T> double calculateAverage(List<T> paramsList, CharacteristicType chrType) {
        double average = 0.0;
        if (!paramsList.isEmpty()) {
            MyLogger.log(Level.INFO, "Calculating average...");
            if (chrType == CharacteristicType.substance) {
                average = paramsList.stream().map((element) -> {
                    return ((SolutionParameters)element).getSubstance();
                }).mapToDouble(Double::doubleValue).average().getAsDouble();
            } else if (chrType == CharacteristicType.solution) {
                average = paramsList.stream().map((element) -> {
                    return ((SolutionParameters)element).getSolution();
                }).mapToDouble(Double::doubleValue).average().getAsDouble();
            } else if (chrType == CharacteristicType.percent) {
                average = paramsList.stream().map((element) -> {
                    return ((SolutionParameters)element).getPercent();
                }).mapToDouble(Double::doubleValue).average().getAsDouble();
            }
        }

        MyLogger.log(Level.INFO, "Average - " + average);
        return average;
    }

    public <T> double findMin(List<T> paramsList, CharacteristicType chrType) {
        double min = 0.0;
        if (!paramsList.isEmpty()) {
            MyLogger.log(Level.INFO, "Calculating min...");
            if (chrType == CharacteristicType.substance) {
                min = paramsList.stream().map((element) -> {
                    return ((SolutionParameters)element).getSubstance();
                }).mapToDouble(Double::doubleValue).min().getAsDouble();
            } else if (chrType == CharacteristicType.solution) {
                min = paramsList.stream().map((element) -> {
                    return ((SolutionParameters)element).getSolution();
                }).mapToDouble(Double::doubleValue).min().getAsDouble();
            } else if (chrType == CharacteristicType.percent) {
                min = paramsList.stream().map((element) -> {
                    return ((SolutionParameters)element).getPercent();
                }).mapToDouble(Double::doubleValue).min().getAsDouble();
            }
        }

        MyLogger.log(Level.INFO, "Min - " + min);
        return min;
    }

    public <T> double findMax(List<T> paramsList, CharacteristicType chrType) {
        double max = 0.0;
        if (!paramsList.isEmpty()) {
            MyLogger.log(Level.INFO, "Calculating max...");
            if (!paramsList.isEmpty()) {
                if (chrType == CharacteristicType.substance) {
                    max = paramsList.stream().map((element) -> {
                        return ((SolutionParameters)element).getSubstance();
                    }).mapToDouble(Double::doubleValue).max().getAsDouble();
                } else if (chrType == CharacteristicType.solution) {
                    max = paramsList.stream().map((element) -> {
                        return ((SolutionParameters)element).getSolution();
                    }).mapToDouble(Double::doubleValue).max().getAsDouble();
                } else if (chrType == CharacteristicType.percent) {
                    max = paramsList.stream().map((element) -> {
                        return ((SolutionParameters)element).getPercent();
                    }).mapToDouble(Double::doubleValue).max().getAsDouble();
                }
            }
        }

        MyLogger.log(Level.INFO, "Max - " + max);
        return max;
    }

    public Object getCache() {
        return cacheService;
    }

}
