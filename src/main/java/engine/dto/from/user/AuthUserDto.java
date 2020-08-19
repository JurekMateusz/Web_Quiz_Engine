package engine.dto.from.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDto {
  @NotNull
  @Pattern(regexp = ".+@.+\\..+", message = "Invalid email format")
  private String email;

  @NotNull
  @Size(min = 5)
  private String password;
}
