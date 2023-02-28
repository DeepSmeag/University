clf;
hold on;
axis square;
rectangle('Position', [0 0 1 1]);
title('Random points in a 1x1 square');
possibleN = [500,1000,2000];
N = possibleN(randi(3));
randPoints = rand(N,2);
% pdist(randPoints)
% colorVector = pdist2(randPoints, ones(size(randPoints),2) * 0.5)(:,1) < 0.5;
colorVector = randPoints - 0.5;
colorVector = colorVector .**2;
colorVector = sum(colorVector,2);
circleDist = colorVector;
colorVector = colorVector < 0.25;
% colorVector(1:100)
% colorVector = colorVector < 0.5;
% colorVector(1:200)
colorMapVector = [colorVector zeros(size(colorVector),1) ones(size(colorVector),1) - colorVector];
% for i = 1:size(randPoints,1)
%     if(colorVector(i) == 1)
%         plot(randPoints(i,1),randPoints(i,2), 'or')
%     else
%         plot(randPoints(i,1),randPoints(i,2), 'ob')
%     endif
% endfor
% plot(randPoints, 'o', 'Color', colorMapVector)
corner1Dist = sum((randPoints - [0 0]).**2,2);
corner2Dist = sum((randPoints - [0 1]).**2,2);
corner3Dist = sum((randPoints - [1 1]).**2,2);
corner4Dist = sum((randPoints - [1 0]).**2,2);
cornerDist = [corner1Dist corner2Dist corner3Dist corner4Dist];
cornerDistmin = min(cornerDist, [],2);
cornerDistmin = cornerDistmin < circleDist;

decisionDist = cornerDistmin < circleDist;
colorMapVector(decisionDist > 0,1)= 0;
colorMapVector(decisionDist > 0,2)= 1;
colorMapVector(decisionDist > 0,3)= 0;

triangle1Dist = sum((randPoints - [0 0.5]).**2,2);
triangle2Dist = sum((randPoints - [0.5 1]).**2,2);
triangle3Dist = sum((randPoints - [0.5 0]).**2,2);
triangle4Dist = sum((randPoints - [1 0.5]).**2,2);

triangleDistmin = [triangle1Dist triangle2Dist triangle3Dist triangle4Dist];
triangleDistmin = triangleDistmin < 0.25;
triangleDistmin = sum(triangleDistmin,2);

colorMapVector(triangleDistmin == 2,1) = 1;
colorMapVector(triangleDistmin == 2,2) = 1;
colorMapVector(triangleDistmin == 2,3) = 0;



scatter(randPoints(:,1),randPoints(:,2), 20, colorMapVector)

