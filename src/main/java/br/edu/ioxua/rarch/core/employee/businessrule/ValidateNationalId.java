package br.edu.ioxua.rarch.core.employee.businessrule;

import br.edu.ioxua.rarch.core.businessrule.BusinessRule;
import br.edu.ioxua.rarch.core.employee.Employee;
import br.edu.ioxua.rarch.core.result.Message;
import br.edu.ioxua.rarch.core.result.OperationResult;
import br.edu.ioxua.rarch.di.annotation.Group;
import br.edu.ioxua.rarch.di.annotation.Injectable;
import br.edu.ioxua.rarch.di.annotation.Named;

@Group("SAVE")
@Named(clazz = Employee.class)
@Injectable
public class ValidateNationalId implements BusinessRule<Employee> {
    // From https://www.vivaolinux.com.br/script/Codigo-para-validar-CPF-e-CNPJ-otimizado
    private static final int[] CPF_WEIGHT = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    private static int calculateValidatorDigit(String str) {
        int sum = 0;
        for (int i=str.length()-1, digit; i >= 0; i-- ) {
            digit = Integer.parseInt(str.substring(i,i+1));
            sum += digit*CPF_WEIGHT[CPF_WEIGHT.length-str.length()+i];
        }
        sum = 11 - sum % 11;
        return sum > 9 ? 0 : sum;
    }

    private static boolean isValidCPF(String cpf) {
        if (null == cpf || 11 != cpf.length()) return false;

        int digito1 = calculateValidatorDigit(cpf.substring(0,9));
        int digito2 = calculateValidatorDigit(cpf.substring(0,9) + digito1);
        return cpf.equals(cpf.substring(0,9) + "" + digito1 + "" + digito2);
    }

    @Override
    public void process(OperationResult.OperationResultBuilder builder, Employee entity) {
        if (null == entity.getNationalId()) {
            builder.message(
                    Message.error("The Employee national id is required")
            );
            return;
        }

        String cleanCpf = entity.getNationalId().replaceAll("[-\\.]", "");

        if ( !isValidCPF(cleanCpf) ) {
            builder.message(
                    Message.error("The Employee national id is invalid")
            );
        }
    }
}
