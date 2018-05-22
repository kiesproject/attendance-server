package filters
import javax.inject.Inject
import play.api.http.DefaultHttpFilters
import play.filters.cors.CORSFilter
import play.filters.headers.SecurityHeadersFilter

class Filters @Inject() (securityHeadersFilter: SecurityHeadersFilter, corsFilter: CORSFilter)
  extends DefaultHttpFilters(securityHeadersFilter, corsFilter)