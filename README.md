MsgX - micro application to encrypt/decrypt text or file with password.

usage examples:

1) encrypt text from clipboard to clipboard

java -jar MsgX.jar e sk=my_secret_key iv=AA22BB44

(iv - InitVector fixed length random initialization seed)

2) encrypt defined text to clipboard

java -jar MsgX.jar e sk=my_secret_key iv=AA22BB44 msg=text_to_encrypt

3) encrypt defined file (upto 256Kb) to clipboard as-encrypted-text

java -jar MsgX.jar ef sk=my_secret_key iv=AA22BB44 file=path_to_file_read

4) decrypt text from clipboard to clipboard

java -jar MsgX.jar d sk=my_secret_key iv=AA22BB44

5) decrypt file as-encrypted-text from clipboard to file

java -jar MsgX.jar df sk=my_secret_key iv=AA22BB44 file=path_to_file_write