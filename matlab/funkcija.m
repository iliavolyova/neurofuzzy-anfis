function [ f ] = funkcija( x , y )
%FUNKCIJA vraca vrijednost zadane fje za parametre x i y
f = ((x-1)^2 + (y+2)^2 - 5*x*y + 3)*(cos(x/5))^2;
end

