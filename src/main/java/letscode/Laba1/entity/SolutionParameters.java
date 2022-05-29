package letscode.Laba1.entity;

public class SolutionParameters {
    double solution;
    double substance;
    double percent;

    public SolutionParameters(double solution, double substance, double percent) {
        this.solution = solution;
        this.substance = substance;
        this.percent = percent;
    }

    public SolutionParameters() {
        this.solution = 0;
        this.substance = 0;
        this.percent = 0;
    }

    public double getSolution() {
        return solution;
    }

    public void setSolution(double solution) {
        this.solution = solution;
    }

    public double getSubstance() {
        return substance;
    }

    public void setSubstance(double substance) {
        this.substance = substance;
    }
    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
