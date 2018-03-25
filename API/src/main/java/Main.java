import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        //ex: https://github.com/mscharhag/blog-examples/tree/master/sparkdemo/src/main/java/com/mscharhag/sparkdemo

        // Skriv ut alla fel
        exception(Exception.class, (e, req, res) -> e.printStackTrace());

        //Ställ in vilken port servern ska köra på
        port(9999);

        //https://sparktutorials.github.io/2016/05/01/cors.html = CORS (Cross-Origin Resource Sharing)
        enableCORS("null", "GET,PUT,POST", "Access-Control-Allow-Origin, Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With" );

        //http://localhost:9999/hello
        get("/user/:eid/:password", (req, res) -> new User().getUser(req.params("eid"), req.params("password")), JsonUtil.json());
        get("/courses/:eid", (req, res) -> new Course().getCourses(req.params("eid")), JsonUtil.json());
        get("/course/:cid", (req, res) -> new Course().getCourse(req.params("cid")), JsonUtil.json());
        get("/course/:cid/parts", (req, res) -> new CoursePart().getCourseParts(req.params("cid")), JsonUtil.json());
        get("/parts/:part", (req, res) -> new CoursePart().getCoursePart(req.params("part")), JsonUtil.json());
        //Skicka tillbaka datan som json datatypen
        after((req, res) -> {
            res.type("application/json");
        });
    }

    // Enables CORS on requests. This method is an initialization method and should be called once.
    private static void enableCORS(final String origin, final String methods, final String headers) {

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });
    }
}