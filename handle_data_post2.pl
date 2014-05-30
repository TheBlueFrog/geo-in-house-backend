#!/usr/bin/perl
	
#	use File::Path qw(make_path);

    my($local_files) = '/data';

    local ($buffer, @pairs, $pair, $name, $value, %FORM);
    # Read in text
    $ENV{'REQUEST_METHOD'} =~ tr/a-z/A-Z/;
    if ($ENV{'REQUEST_METHOD'} eq "POST")
    {
        read(STDIN, $buffer, $ENV{'CONTENT_LENGTH'});
    }
    else
    {
		$buffer = $ENV{'QUERY_STRING'};
    }

    $customer_id = $ENV{'HTTP_X_GUID'};
    $dataset_path = $ENV{'HTTP_X_PATH'};
    $dataset_name = $ENV{'HTTP_X_NAME'};
    $the_data  = $buffer;


    # Split information into name/value pairs put in a table
#    @pairs = split(/&/, $buffer);
#    foreach $pair (@pairs)
#    {
#		($name, $value) = split(/=/, $pair);
#		$value =~ tr/+/ /;
#		$value =~ s/%(..)/pack("C", hex($1))/eg;
#		$FORM{$name} = $value;
#    }
#    $customer_id = $FORM{customer_id};
#    $dataset_path = $FORM{dataset_path};
#    $dataset_name = $FORM{dataset_name};
#    $the_data  = $FORM{the_data};
    
    
	$path = "$local_files/uploads/$customer_id/$dataset_path";
		
print "Content-type:text/html\r\n\r\n";
print "<html>";
print "<head>";
print "<title>Received POST</title>";
print "</head>";
print "<body>";
print "local files: $local_files/uploads <br>";
print "customer: $customer_id <br>";
print "dataset path: $dataset_path <br>";
print "dataset name: $dataset_name <br>";
print "local path: $path/$dataset_name <br>";
#print "data: $buffer <br>";

#if (0)
#{
  if ( ! (-e $path))
  {
    print "whole path does not exist, create $path<br>";

    $x = "$local_files/uploads/$customer_id";
    if ( ! (-e $x))
    {
       print "GUID does not exist, create $customer_id";
       mkdir ($x);
       if ( ! (-e $x))
       {
          print "failed to create dir for GUID $customer_id";
       }
    }

    @ds = split(/\//, $dataset_path);
    foreach $d (@ds)
    {
      $x = "$x/$d"; 
      if ( ! (-e $x))
      {
         print "try to create $x<br>";
         mkdir ($x);
         if ( ! (-e $x))
	 {
            print "failed to create $x<br>";
         }
      }
      else
      {
         print "this exists $x<br>";
      }
    }
  }

  if ( ! (-e $path))
  {
    print "cannot setup path $path, data not uploaded";
  }
  else
  {	
    open (MYFILE, ">$path/$dataset_name");
    print MYFILE "$the_data\n";
    close (MYFILE);

	$the_data = "";
    print "<h2>OK, data uploaded to $path/$dataset_name</h2>";
}
#}

print "</body>";
print "</html>";

1;

