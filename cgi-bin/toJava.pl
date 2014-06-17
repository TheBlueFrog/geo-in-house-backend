#!/usr/bin/perl
	
  my($local_files) = '/var/www/data';

  local ($buffer, @pairs, $pair, $name, $value, $cmd, $params);
  
  print "Content-type:text/html\r\n\r\n";
  print "<html>";
  print "<head>";
  
  $ENV{'REQUEST_METHOD'} =~ tr/a-z/A-Z/;
  if ($ENV{'REQUEST_METHOD'} ne "POST")
  {
    $buffer = $ENV{'QUERY_STRING'};
    print "<title>Received GET, not supported</title>";
    print "</head><body></body></html>";
    die;
  }

  read(STDIN, $buffer, $ENV{'CONTENT_LENGTH'});
  print "<title>Received POST</title>";

  # take the parameters we have, pass along to Java except that the
  # one named "OpCode" is the name of the class to call
    
  # the Apache process has no access to most environment vars,
  # so we do it by hand, pretty fragile...
  $javalibs = `ls /var/www/cgi-bin/lib/*.jar | tr "\n" " " | tr " " ":"`;

  $cmd = "java -cp $javalibs:/var/www/cgi-bin/bin com.ebay.mike.";
  $params = "";
  
  @pairs = split(/&/, $buffer);
  foreach $pair (@pairs)
  {
    ($name, $value) = split(/=/, $pair);
  	$value =~ tr/+/ /;
  	$value =~ s/%(..)/pack("C", hex($1))/eg;

    if ($name eq "OpCode")
    {
      $cmd = "$cmd$value";
    }
    else
    {
      $params = "$params $name \"$value\"";
    }
  }


  # now pass to Java, output the response
  
  print "<br>";
  print "execute $cmd $params";
  print "<br>";
  print `$cmd $params`;
  print "<br>";
  
  print "</body>";
  print "</html>";

1;

