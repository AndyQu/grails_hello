<table>
    <tr><th>Name</th><th>Gender</th></tr>
    <g:each in="${customerList}" var="cust">
        <tr><td>${cust.lastName}, ${cust.firstName}</td><td>${cust.gender}</td></tr>
    </g:each>
</table>