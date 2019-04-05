public class URL {
    private static final String HTTPS = "https://",HTTP = "http://";
    private static final String NO_HTTPS_MESSAAGE = "URL MUST HAVE "+HTTPS;
    private static final String NO_WWW_MESSAGE = "URL MUST HAVE WWW.";
    public static final String _OK = "OK";
    static String chekURL(String url){
        url = url.toLowerCase().trim();
        if(!url.startsWith(HTTPS) && !url.contains(HTTP))
            return NO_HTTPS_MESSAAGE;
        return _OK;
    }
}
