package acme.me.j2ee;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import java.util.Set;

/**
 * @author: cdchenmingxuan
 * @date: 2019/8/9 11:24
 * @description: java-feature-view
 */
@Getter
@Setter
@Slf4j
public class Jsr303Validate {
    /**
     * @JSR-303
     * 14      * Bean Validation 中内置的 constraint
     * 15      * @Null   被注释的元素必须为 null
     * 16      * @NotNull    被注释的元素必须不为 null
     * 17      * @AssertTrue     被注释的元素必须为 true
     * 18      * @AssertFalse    被注释的元素必须为 false
     * 19      * @Min(value)     被注释的元素必须是一个数字，其值必须大于等于指定的最小值
     * 20      * @Max(value)     被注释的元素必须是一个数字，其值必须小于等于指定的最大值
     * 21      * @DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值
     * 22      * @DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值
     * 23      * @Size(max=, min=)   被注释的元素的大小必须在指定的范围内
     * 24      * @Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内
     * 25      * @Past   被注释的元素必须是一个过去的日期
     * 26      * @Future     被注释的元素必须是一个将来的日期
     * 27      * @Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式
     * 28      * Hibernate Validator 附加的 constraint
     * 29      * @NotBlank(message =)   验证字符串非null，且长度必须大于0
     * 30      * @Email  被注释的元素必须是电子邮箱地址
     * 31      * @Length(min=,max=)  被注释的字符串的大小必须在指定的范围内
     * 32      * @NotEmpty   被注释的字符串的必须非空
     * 33      * @Range(min=,max=,message=)  被注释的元素必须在合适的范围内
     * 34
     */
    private Long id;

    @Max(value = 20, message = "{val.age.message}")
    private Integer age;

    @NotBlank(message = "{username.not.null}")
    @Length(max = 6, min = 3, message = "{username.length}")
    private String username;

    @NotBlank(message = "{pwd.not.null}")
    @Pattern(regexp = "/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$/", message = "密码必须是6~10位数字和字母的组合")
    private String password;

    @Pattern(regexp = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message = "手机号格式不正确")
    private String phone;

    @Email(message = "{email.format.error}")
    private String email;

    @Test
    public void test1() {
        Validator validator = Validation
                .byProvider(HibernateValidator.class).configure().failFast(false).buildValidatorFactory().getValidator();

        Jsr303Validate t = new Jsr303Validate();
        t.setEmail("google");
        t.setPhone("243522");
        t.setAge(21);
        Set<ConstraintViolation<Jsr303Validate>> validate = validator.validate(t);
        // 抛出检验异常
        if (validate.size() > 0) {
            for (ConstraintViolation<Jsr303Validate> validateConstraintViolation : validate) {
                log.error(validateConstraintViolation.getMessage());
            }
        }
    }
}
