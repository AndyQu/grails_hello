<meta name="layout" content="main"/>

<table>
    <tr><th>Name</th><th>Birthday</th></tr>
    <g:each in="${persons}" var="person">
        <tr><td>${person.lastName}, ${person.firstName}</td><td>${person.dateOfBirth}</td></tr>
    </g:each>
</table>