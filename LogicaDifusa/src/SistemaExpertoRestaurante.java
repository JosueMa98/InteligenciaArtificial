import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

public class SistemaExpertoRestaurante {
    public static void main(String[] args) {

    	String fileName = "restaurant_quality.fcl";
        FIS fis = FIS.load(fileName, true);

        if (fis == null) {
            System.err.println("No se pudo cargar el archivo FCL: " + fileName);
            return;
        }


        fis.setVariable("temperatura", 40);
        fis.setVariable("comida", 8);
        fis.setVariable("servicio", 3);


        fis.evaluate();

 
        FunctionBlock fb = fis.getFunctionBlock("restaurant");
        double calidad = fb.getVariable("calidad").getLatestDefuzzifiedValue();
        

        String calidadLinguistica = fb.getVariable("calidad").toString();
        System.out.println("Calidad del restaurante: " + calidadLinguistica);


        // Mostrar el resultado
        System.out.println("Calidad del restaurante: " + calidad);
    }
}