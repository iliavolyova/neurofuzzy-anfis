fid = fopen('params.txt');

A = fscanf(fid, '%g %g %g %g %g %g', [6 inf]);
A = A';

syms x y z;


hold on;
i=1;
y1 = @(x) 1/(1 + (((x-A(i,5))^2)/(A(i,1)*A(i,1)))^A(i,3));
y2 = @(y) 1/(1 + (((y-A(i,6))^2)/(A(i,2)*A(i,2)))^A(i,4));

ezplot(y1, [-40, 40, 0, 1.5]);
h = ezplot(y2, [-40, 40, 0, 1.5]);
set(h,'color','red');
title('funkcije pripadnosti za A6 i B6');
xlabel('ulaz');
ylabel('pripadnost');
