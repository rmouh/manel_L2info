#include "get_next_line.h"
#include<stdio.h> 
#include <fcntl.h> 
/*((
    si on commance par une ligne vide :????
))*/

char    *get_line(char *str)
{
    int i;
    char    *line;

    i = 0;
    while (str[i] && str[i] != '\n')
        i++;
    line = (char *)malloc(sizeof(char) * (i + 2));
	if (!line)
		return (NULL);
	i = 0;
	while (str[i] && str[i] != '\n')
	{
		line[i] = str[i];
		i++;
	}
	if (str[i] == '\n')
	{
		line[i] = str[i];
		i++;
	}
	line[i] = '\0';
    return (line);
}

char    *get_after_line(char *str)
{
    int     i;
    int     j;
    char    *buf;

    i = 0;
    while (str[i] && str[i] != '\n')
        i++;
    
    if (!str[i])
        {
            free(str);
            return (NULL);
        }
        buf = (char *)malloc(sizeof(char) * (ft_strlen(str) - i + 1));
        if (!buf)
            return (NULL);
        i++;
        j = 0;
        while (str[i])
            buf[j++] = str[i++];
        buf[j] = '\0';
        free(str);
        return (buf);
}

char    *creat_line (char *str)
{
    return (ft_strjoin(str,"\n"));
}

int reached_EOF(char *str)
{
    while (*str)
    {
        if(*str == '\n')
            return (1);
        str++;
    }
    return (0);
}

char    *get_next_line(int fd) 
{

    char *s_buf;
    char        *temp;
     char        *line;
    char        *line2;
    int         readed;

    s_buf = (char *)malloc(sizeof(char) * (BUFFER_SIZE + 1));
    if ( !s_buf || fd < 0 || fd > OPEN_MAX ||
     BUFFER_SIZE <= 0 || (read(fd, NULL, 0))== -1)
        return (NULL);
    while ((readed = read (fd, s_buf, BUFFER_SIZE)) > 0)
    {
        s_buf [readed] ='\0';
        line = ft_strjoin(line, s_buf);
        if (reached_EOF(line))
      {
            temp = get_line(line);
            line = get_after_line(line);
            free(s_buf);
            return (temp);
            break ;
        }
    }
    free(s_buf);
    //temp = get_line(line);
    return (!s_buf[0] ? NULL : temp);
}



int main (int argc, char *argv[])
{
    char *buff;
    char *buf;
    //static char *bufff[100];

    int j = 0;

    // int fd3 =open ("test.txt",O_RDONLY);
    //int fd = open ("t.txt",O_RDONLY);
    int fd2 = open ("t.txt",O_RDONLY);
    //printf("treu");

    // buf =ft_strjoin(buff, "iohinbujb");
    // printf("the line after the fonction  :%s",buf);
    //  j = read(fd2, buff, BUFFER_SIZE);
    // printf("the line :%d\n",j);
    // printf("we'v read :%s\n",buff);

    buf = get_next_line(fd2);
printf("%s",buf);
    //free(buf);
    buf = get_next_line(fd2);
       

printf("%s",buf);
        buf = get_next_line(fd2);
       

printf("%s",buf);
    //printf("true");
    free(buf);
    

}














// char    *get_after_line(char *str)
// {
//     int i;

//     i = 0;
//     while (str[i] && str[i] != '\n')
//         i++;
//     return (&str[i + 1]);
// }






// char *get_next_line(int fd)
// {
//     int         i;
//     int         line_size;
//     char        *c;
//     char        *old;
//     char        *new;

    
//     old = ft_strdup("");
//     if (fd < 0 || !line || fd > OPEN_MAX || BUFFER_SIZE <= 0)
//         return (-1);
//     else
//     {
//         if(!(c = (char *)malloc((BUFFER_SIZE + 1) * sizeof(char))))
//             return (-1);
//         while (read(fd, c, BUFFER_SIZE) > 0)
//         {
//             // c[BUFFER_SIZE]= '\0';
//             new = ft_strjoin(old, c);
//             printf("c:  %s\n", c);
//             free(old);
//             printf("new: %s\n", new);
//             old = ft_strdup(new);
//             printf("old: %s\n", new);

//             free(new);
//             if (reached_EOF(c))
//             {
//                 *line = the_line(old);
//                 //free(old);
//                 printf(" the result :%s\n", *line);
//                 free(c);
//                 return (1);
//              }
//         }
//             return (read(fd, c, BUFFER_SIZE));

//         }
    
// }


//  return (0);
//     char **buf;
//     static char *bufff[100];

//     int j = 0;
//     //printf("on a return :\n");
//     //buf[BUFFER_SIZE] ='\0';
//     int fd = open ("t.txt",O_RDONLY);
//     //ft_strjoin("", "qqs");
//     printf("on a return :%d\n",get_next_line(fd, buf));
//     //buf[BUFFER_SIZE] ='\0';
//     // while (buf[j])
//     //     printf(" %c", (buf[j++] ));
    
//     free(*buf);
//     free(*bufff);

// char    *reader(char *line, char *temp, char *s_buf, int fd)
// {
//     int     readed;

//     while ((readed = read (fd, s_buf, BUFFER_SIZE)) >= 0)
//     {
//     printf("the readed :%d\n",readed);

//         temp = line;
        //s_buf [readed] ='\0';
//         line = ft_strjoin(line, s_buf);
//         free(temp);
//         if (reached_EOF(line))
//         {
//             temp = get_after_line(line);
//             line = get_line(line);
//             free(s_buf);
//             return (line);
//         }
//         else if (readed == 0)
//         {
//             temp = creat_line(line);
//             free(line);
//             free(s_buf);
//             return (temp);
//         }
//     }
// }


// char    *reader(char *line, char *temp, char *s_buf, int fd)
// {
//     int     readed;

//     readed = 1;
//     while (!reached_EOF(line) && readed != 0)
//     {
//         readed = read (fd, s_buf, BUFFER_SIZE);
//         temp = line;
//         s_buf[readed] = '\0';
//         line = ft_strjoin(line, s_buf);
//         free(temp);
//     }
//     free(s_buf);
//     return(line);
//             temp = get_after_line(line);
//             line = get_line(line);
//             free(s_buf);
//             return (line);
//         }
//         else if (readed == 0)
//         {
//             temp = creat_line(line);
//             free(line);
//             free(s_buf);
//             return (temp);
//         }
//     }
// }