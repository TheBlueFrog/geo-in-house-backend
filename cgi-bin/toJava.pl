#!/usr/bin/perl

  # logging philo, if all is cool the executed Java code will return
  # a string, that is what we return, unadorned.
  #
  # otherwise we return an HTML formatted error, this is probably wrong
  	
  my($local_files) = '/var/www/data';

  local ($buffer, @pairs, $pair, $name, $value, $cmd, $params);
  
  $ENV{'REQUEST_METHOD'} =~ tr/a-z/A-Z/;
  if ($ENV{'REQUEST_METHOD'} ne "POST")
  {
    $buffer = $ENV{'QUERY_STRING'};

  	print "Content-Type:text/html\r\n\r\n";
  	print "<html>";
	print "<head>";
    print "<title>Error - GET not supported</title>";
    print "</head><body></body></html>";
    die;
  }

  read(STDIN, $buffer, $ENV{'CONTENT_LENGTH'});
#  print "<title>Received POST</title>";

  # take the parameters we have, pass along to Java except that the
  # one named "OpCode" is the name of the class to call
    
  # the Apache process has no access to most environment vars,
  # so we do it by hand, pretty fragile...
  $javalibs = `ls /var/www/cgi-bin/lib/*.jar | tr "\n" " " | tr " " ":"`;

  $opcode = "";
  $params = "";
  
  @pairs = split(/&/, $buffer);
  foreach $pair (@pairs)
  {
    ($name, $value) = split(/=/, $pair);
  	$value =~ tr/+/ /;
  	$value =~ s/%(..)/pack("C", hex($1))/eg;

    if ($name eq "OpCode")
    {
      $opcode = "$value";
    }
    else
    {
      $params = "$params $name \"$value\"";
    }
  }

  if ($opcode eq "")
  {
    # oops no opcode
  
  	print "Content-Type:text/html\r\n\r\n";
  	print "<html>";
	print "<head>";
    print "<title>Error - no OpCode</title>";
    print "</head><body></body></html>";
  }
  else
  {
    # now pass to Java, output the response
  
    $cmd = "java -cp $javalibs:/var/www/cgi-bin/bin com.ebay.mike.$opcode";

#  	print "Content-Type:text/html\r\n\r\n";
#  	print "<html>";
#	print "<head>";
#    print "<title>OpCode $opcode</title>";
#    print "</head><body>$cmd $params</body></html>";
    
  	print "Content-Type:text\r\n\r\n";
    $response = `$cmd $params`;
    print "$response";
  }
  
1;

