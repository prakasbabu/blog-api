package com.prakash.payloads;





import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	

	private Integer categoryId;
	@NotBlank
	@Size(min=4)
	private String categoryTitle;
	@NotBlank
	@Size(max=100)
	private String categoryDescription;
}
