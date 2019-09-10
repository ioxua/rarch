<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<t:commonTemplate pageName="Dashboard">
    <jsp:attribute name="styles">
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.css"
              integrity="sha256-aa0xaJgmK/X74WM224KMQeNQC2xYKwlAt08oZqjeF0E="
              crossorigin="anonymous"> </link>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js"
                integrity="sha256-Uv9BNBucvCPipKQ2NS9wYpJmi8DTOEfTA/nH2aoJALw="
                crossorigin="anonymous"></script>
        <script>

            var randomScalingFactor = function () {
                return Math.round(Math.random() * 100);
            };

            var config = {
                type: 'pie',
                data: {
                    datasets: [{
                        data: [
                            randomScalingFactor(),
                            randomScalingFactor(),
                            randomScalingFactor(),
                            randomScalingFactor(),
                            randomScalingFactor()
                        ],
                        backgroundColor: [
                            window.chartColors.red,
                            window.chartColors.orange,
                            window.chartColors.yellow,
                            window.chartColors.green,
                            window.chartColors.blue,
                        ],
                        label: 'Dataset 1'
                    }],
                    labels: [
                        'Red',
                        'Orange',
                        'Yellow',
                        'Green',
                        'Blue'
                    ]
                },
                options: {
                    responsive: true
                }
            };

            $(function() {
                var ctx = $('#chart')[0].getContext('2d');
                window.myPie = new Chart(ctx, config);
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="ui four column doubling stackable grid container">
            <div class="column">
                <div class="cards">
                    <div class="card">
                        <div class="content">
                            <div class="header">Foo</div>
                            <canvas id="chart"></canvas>
                        </div>
                    </div>
                </div>
            </div>
            <div class="column">
            </div>
        </div>
        <!-- /container -->
    </jsp:body>
</t:commonTemplate>