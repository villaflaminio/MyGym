# Interceptor

Intercetta e gestisce tutte le chiamate http, nel mio caso dato che la maggior parte delle chiamate sono autenticate l’Interceptor recupera il token ricevuto in fase di login dal localStorage e aggiunge o meno il token alla chiamata a seconda che l’utente è autenticato o meno.

```tsx
intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {

    // Add auth header with jwt if user is logged in and request is to Api Url
    const user = JSON.parse(localStorage.getItem("currentUser")!);
    const isLoggedIn = user && user.id_token && user.user;
    const isApiUrl = request.url.startsWith(environment.apiUrl);

    //check if user is logged
    if (isLoggedIn && isApiUrl) {
      //attach the autorization token 
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${user.id_token}`,
        },
      });
    }

    // If user is not logged in or we are not searching for Api Url, we have same request as before
    return next.handle(request);
  }
```