<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@attribute name="scripts" fragment="true"%>
<%@attribute name="styles" fragment="true"%>
<%@attribute name="pageName" required="true"%>

<!-- https://stackoverflow.com/a/3257426-->
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>RArch - ${pageName}</title>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css"
          integrity="sha256-9mbkOfVho3ZPXfM7W8sV2SndrGDuh7wuyLjtsWeTI1Q="
          crossorigin="anonymous"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mods.css">
    <jsp:invoke fragment="styles"/>
</head>
<body>
<div class="ui left vertical menu sidebar">
    <div class="item">
        <img class="ui mini image" src="/images/logo.png">
    </div>
    <div class="item">
        <div class="ui input"><input type="text" placeholder="Search..."></div>
    </div>

    <a href="../../dashboard.jsp"
       class="item${pageContext.request.requestURI.contains('/dashboard.jsp') ? ' active' : ''}">
        <i class="chart pie icon"></i> Dashboard
    </a>
    <a href="../../employees.jsp"
       class="item${pageContext.request.requestURI.contains('/employees.jsp') ? ' active' : ''}">
        <i class="user icon"></i> Employees
    </a>
    <a href="../../incidents.jsp"
       class="item${pageContext.request.requestURI.contains('/incidents.jsp') ? ' active' : ''}">
        <i class="exclamation icon"></i> Incidents
    </a>

    <a href="#" class="ui red item">
        <i class="red power off icon"></i> Sign out
    </a>
</div>
<div class="pusher">
    <div class="ui borderless huge br-0 menu">
        <a href="#" class="item sidebar toggle"><i class="content icon"></i></a>
        <div class="item">${pageName}</div>

        <div class="right menu">
            <div class="ui dropdown item">
                Language <i class="dropdown icon"></i>
                <div class="menu">
                    <a class="item"><i class="us flag"></i> English</a>
                    <a class="item"><i class="br flag"></i> Portuguese</a>
                </div>
            </div>
            <%--<div class="item">--%>
                <%--<div class="ui primary button">Sign Up</div>--%>
            <%--</div>--%>
        </div>
    </div>
    <jsp:doBody/>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js"
        integrity="sha256-t8GepnyPmw9t+foMh3mKNvcorqNHamSKtKRxxpUEgFI="
        crossorigin="anonymous"></script>
<script>
    $(function () {
        $('.sidebar.toggle').on('click', function () {
            $('.ui.sidebar').sidebar('toggle');
        });
        $('.ui.dropdown').dropdown();
    });
</script>
<jsp:invoke fragment="scripts"/>
</body>
</html>
