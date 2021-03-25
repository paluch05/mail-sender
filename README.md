# Mailsender app

In this repository I'm working on a simple mail sending program using Spring.

## How it works?

This app sends emails to a group of recipients. Reads recipients' addresses and names, subject, 
template(body) and attachments from files.

## To run application:
1. In `resources` folder create `application-local.yml` file, set there paths to directories 
from where template, subject and recipients list files will be read. Also put there sender's email 
address and password to his account. Schema:

    ``` java
   mail:
     templates:
       folder: D:\Users\user\Desktop\mail
     recipients:
       folder: D:\Users\user\Desktop\mail
   spring:
     mail:
       username: yourMail@gmail.com
       host: smtp.gmail.com
       password: password
  
   ``` 
    
    * __recipients__ list should be in `.txt` format. Schema:
    
                recipientsName,recipientsAddress@gmail.com
                anotherRecipientsName,anotherRecipientsAddress@hotmail.com
                
    * __subject__ also in `.txt`. You can write anything in here!
    
    * __template__ must be in `.ftl`. I recommend starting it with:
    
                                        Hello, ${name}! 
                                        
    Thanks to that you will greet every single one of the recipients at the beginning of your message, 
    what will make them feel special :smile: 
           
    * __attachments__ should have their own folder. Put any format in here, .jpg, .pdf, anything you
    need!
    
    * there's one main folder for recipients list, subject and template files. Folder with attachments 
    should be in that main folder. Schema:
    
            mail
                |__attachments
                |             |__image.jpg
                |             |__pdf.pdf
                |__subject.txt 
                |__recipients.txt
                |__template.ftl
          
      
    
2. In configurations set profiles `active=local`
3. Run Spring Application
4. In your browser write: localhost8080:mail/yourTemplateName/yourRecipientsListName 


## _Done!_

Milions of your recipients got the same mail, but a bit personalized.

If you have any questions, let me know!
