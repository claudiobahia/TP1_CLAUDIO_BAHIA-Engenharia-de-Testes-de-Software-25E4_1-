import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*todo Parte 2 e 3
Identificação de problemas -- Falhas no código
1. Código de classificação está repetitivo, verificação imc == 16 é desnecessária, por exemplo.
2. Código de classificação deveria usar && ao invés de ||
    if (imc < 16.0) {
        return "Magreza grave";
    }
    else if (imc == 16.0 || imc < 17.0) {
        return "Magreza moderada";
    }

3. Não foi possível simular o código rodando por conta da estrutura do mesmo, apenas 1 Scanner é necessário.
4. O projeto não está baseado em Maven
5. CalcularPeso calcula IMC, não peso.
6. Isolar a lógica de cálculo e classificação da parte de interação com o usuário para facilitar testes automatizados.
*/
//todo código é Parte 1 e 4
public class CalculoIMCTests {

    double peso = 75;
    double altura = 1.81;

    @Test@DisplayName("RN02 - esperado Magreza grave")
    void classificarMagrezaGrave() {
        assertEquals("Magreza grave", CalculoIMC.classificarIMC(-123));
    }

    @Test@DisplayName("RN03 -  - esperado Magreza moderada")
    void classificarMagrezaModerada() {
        assertEquals("Magreza moderada", CalculoIMC.classificarIMC(16.0));
        assertEquals("Magreza moderada", CalculoIMC.classificarIMC(16.5));
    }

    @Test@DisplayName("RN04  - esperado Magreza leve")
    void classificarMagrezaLeve() {
        assertEquals("Magreza leve", CalculoIMC.classificarIMC(17.0));
        assertEquals("Magreza leve", CalculoIMC.classificarIMC(18.4));
    }

    @Test@DisplayName("RN05 - esperado Saudavel")
    void classificarSaudavel() {
        assertEquals("Saudável", CalculoIMC.classificarIMC(18.5));
        assertEquals("Saudável", CalculoIMC.classificarIMC(24.9));
    }

    @Test@DisplayName("RN06 - esperado Sobrepreso")
    void classificarSobrepeso() {
        assertEquals("Sobrepeso", CalculoIMC.classificarIMC(25.0));
        assertEquals("Sobrepeso", CalculoIMC.classificarIMC(29.9));
    }

    @Test@DisplayName("RN07 - esperado Obesidade I")
    void classificarObesidadeGrauI() {
        assertEquals("Obesidade Grau I", CalculoIMC.classificarIMC(30.0));
        assertEquals("Obesidade Grau I", CalculoIMC.classificarIMC(34.9));
    }

    @Test@DisplayName("RN08 - esperado Obesidade II")
    void classificarObesidadeGrauII() {
        assertEquals("Obesidade Grau II", CalculoIMC.classificarIMC(35.0));
        assertEquals("Obesidade Grau II", CalculoIMC.classificarIMC(39.9));
    }

    @Test@DisplayName("RN09 - esperado Obesidade III")
    void classificarObesidadeGrauIII() {
        assertEquals("Obesidade Grau III", CalculoIMC.classificarIMC(40.0));
        assertEquals("Obesidade Grau III", CalculoIMC.classificarIMC(50.0));
    }

    @Test@DisplayName("RN10 - esperado peso/(altura*altura)")
    void calcularPeso(){
        assertEquals(peso/(altura*altura), CalculoIMC.calcularPeso(peso,altura));
    }

    @Test@DisplayName("RN11 - divisão com double = 0 gera Infinty")
    void calcularPesoZero(){
        assertTrue(Double.isInfinite(CalculoIMC.calcularPeso(peso, 0)));
        }

    @Test@DisplayName("RN12 - Divisão por peso negativo não gera exception")
    void calcularPesoNegativo(){
        assertDoesNotThrow(() -> CalculoIMC.calcularPeso(peso, -1));
    }

    @Test@DisplayName("RN13 - n fiz funcionar")
    void executarCalculoIMC(){
        // --- 1ª parte: simula a primeira leitura (peso) ---
        String inputPeso = "70\n";
        System.setIn(new ByteArrayInputStream(inputPeso.getBytes()));

        // Captura a saída
        ByteArrayOutputStream fakeOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeOut));

        // Executa o método até a primeira leitura (o programa cria o primeiro Scanner)
        // Depois da primeira leitura, ele vai pedir a altura
        // Então, antes de ele tentar ler a altura, trocamos o System.in
        new Thread(() -> {
            try {
                Thread.sleep(100); // pequena pausa pra sincronizar
                // --- 2ª parte: envia a altura quando o segundo Scanner for criado ---
                String inputAltura = "1.75\n";
                System.setIn(new ByteArrayInputStream(inputAltura.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // Agora executa o programa
        CalculoIMC.programaIMC("1.0");

        // Converte saída pra string
        String output = fakeOut.toString();

        // Asserções
        assertTrue(output.contains("Cálculo do IMC - Versão 1.0"));
        assertTrue(output.contains("Seu índice de massa corporal é"));
        assertTrue(output.contains("Classificação: Saudável"));

        // Mostra a saída
        System.setOut(System.out);
        System.out.println(output);

        // Restaura streams
        System.setIn(System.in);
    }

}