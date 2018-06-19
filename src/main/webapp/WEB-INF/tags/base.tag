<%@tag description="Plantilla Principal" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="stylesheet" href="/css/bootstrap.css" type="text/css">
        <link rel="stylesheet" href="/css/bootstrap-grid.css" type="text/css">
        <link rel="stylesheet" href="/css/bootstrap-reboot.css" type="text/css">
    </head>
    <body>
        <jsp:include page="../jsp/encabezado.jsp" />
        <main role="main">
            <jsp:doBody />
        </main>
        <jsp:include page="../jsp/piedepagina.jsp" />

        <script src="/js/bootstrap.bundle.js"></script>
        <script src="/js/bootstrap.js"></script>
    </body>
</html>