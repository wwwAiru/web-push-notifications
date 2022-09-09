<h1>Web push notifications for browsers.</h1>

1. Generate required a private and public key pair for your server with `npx web-push generate-vapid-keys` and put in Dockerfile(ENV)
2. mvn clean package
3. docker build . --tag web-push
4. docker run -ti --rm -p 8080:8080 web-push
5. Open browser on http://localhost:8080
6. Click button to subscribe.
7. Send POST request to http://localhost:8080/api/v1/push-message
  with body:</br> 
  {</br> 
    "title": "your title",</br> 
    "clickTarget": "your link",</br> 
    "message": "text message"</br> 
  }</br> 

