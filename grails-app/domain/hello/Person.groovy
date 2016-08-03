package hello

import org.apache.commons.lang3.builder.ReflectionToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle

class Person {
	String firstName
	String lastName
	Date dateOfBirth=Date.parse("yyyy-MM-dd","2016-09-05")
	double salary
	@Override
	String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
