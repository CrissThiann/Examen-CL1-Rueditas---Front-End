package pe.edu.cibertec.Examen_Rueditas.Front_End.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RuedasRequestDTO {


    @NotBlank(message = "El campo placa es requerido")
    @Pattern(regexp = "^[A-Z0-9]{3}-[0-9]{3}$", message = "Debe ingresar una placa alfanumérica con el formato correcto (AAA-999)")
    @Size(min = 7, max = 7, message = "La placa debe tener 7 caracteres")
    private String placa;

    public RuedasRequestDTO(String placa) {
        this.placa = placa;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
