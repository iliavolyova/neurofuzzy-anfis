fid = fopen('uzorci.txt', 'w');

for i=-4:4
    for j=-4:4
        fprintf(fid,'%d %d %f\n', i, j, funkcija(i,j));
    end;
end;

fclose(fid);