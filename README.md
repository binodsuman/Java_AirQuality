# Fetch Air Quality Index using Java Code 

First create your account on www.Data.gov.in and generate API KEY. Suppose your key is API_KEY

Mehod is GET Only

It will first 10 records, you can get all filters from this api.

https://api.data.gov.in/resource/3b01bcb8-0b14-4abf-b6f2-c1bfd384ba69?api-key=API_KEY&format=json&offset=0

For State Karnataka
https://api.data.gov.in/resource/3b01bcb8-0b14-4abf-b6f2-c1bfd384ba69?api-key=API_KEY&format=json&offset=0&filters[state]=Karnataka

For Bengaluru City
https://api.data.gov.in/resource/3b01bcb8-0b14-4abf-b6f2-c1bfd384ba69?api-key=API_KEY&format=json&offset=0&filters[city]=Bengaluru

For Telangana State
https://api.data.gov.in/resource/3b01bcb8-0b14-4abf-b6f2-c1bfd384ba69?api-key=API_KEY&format=json&offset=0&filters[state]=Telangana


Output:<br>

*************** Air Pollution Level *********************<br>
City :Chennai<br>
State :TamilNadu<br>
On :22-02-2022 09:00:00<br>
Min Pollution :33<br>
Average Pollution :54<br>
Max Pollution :77<br>
*****************************<br>
