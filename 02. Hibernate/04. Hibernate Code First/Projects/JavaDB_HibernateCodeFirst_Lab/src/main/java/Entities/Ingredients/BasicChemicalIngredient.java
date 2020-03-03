package Entities.Ingredients;

import interfaces.Ingredients.ChemicalIngredient;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class BasicChemicalIngredient extends BasicIngredient implements ChemicalIngredient {
    @Column(name = "chemical_formula")
    private String chemicalFormula;

    protected BasicChemicalIngredient() {}

    protected BasicChemicalIngredient(String name, BigDecimal price, String chemicalFormula) {
        super(name, price);
        this.setChemicalFormula(chemicalFormula);
    }

    @Override
    public String getChemicalFormula() {
        return chemicalFormula;
    }

    @Override
    public void setChemicalFormula(String chemicalFormula) {
        this.chemicalFormula = chemicalFormula;
    }
}
