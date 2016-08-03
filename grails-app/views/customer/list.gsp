<meta name="layout" content="test"/>

<table>
    <tr><th>Name</th><th>Gender</th></tr>
    <g:each in="${customerList}" var="cust">
        <tr><td>${cust.lastName}, ${cust.firstName}</td><td>${cust.gender}</td></tr>
    </g:each>
</table>
    <div>
	    <g:each in="${(1..10)}" var="number">
	        ${number}<br/>
	    </g:each>
    </div>
