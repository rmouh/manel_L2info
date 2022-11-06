#include "he.h"
#include<stdio.h> 
#include <fcntl.h> 

char	*read_and_stock(int fd, char *storage_buffer)
{
	char	*reading_buffer;
	int		readed;

	reading_buffer = malloc(sizeof(char) * (BUFFER_SIZE + 1));
	if (!reading_buffer)
		return (NULL);
	readed = 1;
	while (!ft_strchr(storage_buffer, '\n') && readed != 0)
	{
		
		readed = read(fd, reading_buffer, BUFFER_SIZE);
		printf("kkkkkkkkkkk%s   \n", reading_buffer);

		reading_buffer[readed] = '\0';
		storage_buffer = ft_strjoin(storage_buffer, reading_buffer);
	//printf("kkkkkkkkkkk%s   \n", storage_buffer);

	}
	free(reading_buffer);
	return (storage_buffer);
}

/*
Search a \n or the end of the file anc calcul the length of this line
Malloc a str of this size (line)
Copy all the chars and the \n if needed
Null terminate this string
*/
char	*get_line(char *storage_buffer)
{
	int		i;
	char	*line;

	i = 0;
	if (!storage_buffer[i])
		return (NULL);
	while (storage_buffer[i] && storage_buffer[i] != '\n')
		i++;
	line = (char *)malloc(sizeof(char) * (i + 2));
	if (!line)
		return (NULL);
	i = 0;
	while (storage_buffer[i] && storage_buffer[i] != '\n')
	{
		line[i] = storage_buffer[i];
		i++;
	}
	if (storage_buffer[i] == '\n')
	{
		line[i] = storage_buffer[i];
		i++;
	}
	line[i] = '\0';
	return (line);
}

/*
Search a \n or the end of the file
If there is no more chars in the buffer, free him
Malloc a new buffer with the size of the ancien - the size of the line
Copy to the new buffer the chars they are not in the line
Free ancin buffer and return the new for persistant storage
*/
char	*clear_storage(char *storage_buffer)
{
	int		i;
	int		j;
	char	*buff2;

	i = 0;
	while (storage_buffer[i] && storage_buffer[i] != '\n')
		i++;
	if (!storage_buffer[i])
	{
		free(storage_buffer);
		return (NULL);
	}
	buff2 = (char *)malloc(sizeof(char) * (ft_strlen(storage_buffer) - i + 1));
	if (!buff2)
		return (NULL);
	i++;
	j = 0;
	//printf("%s     ?%d", storage_buffer[i], i);
	while (storage_buffer[i]){
		buff2[j++] = storage_buffer[i++];
	//printf("%s     ?%d", storage_buffer[i], i);
	}
	buff2[j] = '\0';
	free(storage_buffer);
	return (buff2);
}

char	*get_next_line(int fd)
{
	char		*line;
	static char	*storage_buffer;

	if (fd < 0 || BUFFER_SIZE <= 0 || read(fd, NULL, 0) == -1)
		return (NULL);
	storage_buffer = read_and_stock(fd, storage_buffer);
	if (!storage_buffer)
		return (NULL);
	line = get_line(storage_buffer);
	//printf("%s?", storage_buffer);
	storage_buffer = clear_storage(storage_buffer);
	printf("%s         ?\n", storage_buffer);

	return (line);
}

int main (int argc, char *argv[])
{
    char buff[100];
    char *buf;
    //static char *bufff[100];

    int j = 0;

    // int fd3 =open ("test.txt",O_RDONLY);
    //int fd = open ("t.txt",O_RDONLY);
    int fd2 = open ("t.txt",O_RDONLY);

    // buf =ft_strjoin(buff, "iohinbujb");
    // printf("the line after the fonction  :%s",buf);
    buf = get_next_line(fd2);

    printf("the line after the fonction  :%s",buf);
    printf("true");
	buf = get_next_line(fd2);
	
    printf("the line after the fonction  :%s",buf);
    printf("true");
    free(buf);
}