#PARTE 1
  Questões 2 e 3
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
  
  #Código é referente Questões 1 4 e 5


# PARTE 2
