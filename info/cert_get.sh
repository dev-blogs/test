# https://priyalwalpita.medium.com/create-your-own-certificate-authority-47f49d0ba086
# https://blog.regolit.com/2010/02/16/personal-ca-and-self-signed-certificates
# https://dzone.com/articles/securing-rest-apis-with-client-certificates

# ca gen
mkdir ca
cd ca

# ca private key
openssl genrsa -out ca.key 4096
# ca certificate (open key)
openssl req -new -x509 -days 365 -key ca.key -out ca.crt

cd ..

# server gen
mkdir server
cd server
cp ../config.cfg .

# server private key gen
openssl genrsa -out server.key 4096
# server csr gen
openssl req -new -key server.key -config config.cfg -reqexts req_ext -out server.csr
# server certificate signed by ca gen
openssl x509 -req -days 365  -CA ../ca/ca.crt -CAkey ../ca/ca.key -set_serial 01 -extfile config.cfg -extensions req_ext -in server.csr -out server.crt

#convert to pcsk12
openssl pkcs12 -export -out keyStore.p12 -inkey server.key -in server.crt
